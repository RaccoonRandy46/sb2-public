package com.coop.skyblock2.listeners.dungeons.solvers.bomb.chambers;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.coop.skyblock2.listeners.dungeons.solvers.bomb.BombDefuseListener;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public abstract class BDChamber {

	public static final int GROUND = BombDefuseListener.GROUND;
	
	private static final int POS_Z = 1;
	private static final int NEG_Z = 2;
	private static final int POS_X = 3;
	private static final int NEG_X = 4;
	
	public int direction;
	public int xl;
	public int zl;
	public int xr;
	public int zr;
	public double xld;
	public double zld;
	public double xrd;
	public double zrd;
	
	public BDChamber(int[] xzl, int[] xzr, int direction) {
		this.direction = direction;
		this.xl = xzl[0];
		this.zl = xzl[1];
		this.xr = xzr[0];
		this.zr = xzr[1];
		double[] xzld = this.translatedCoordinatesD(this.xl + 0.5, this.zl + 0.5, -0.5, -0.5);
		double[] xzrd = this.translatedCoordinatesD(this.xr + 0.5, this.zr + 0.5, -0.5, -0.5);
		this.xld = xzld[0];
		this.zld = xzld[1];
		this.xrd = xzrd[0];
		this.zrd = xzrd[1];
	}
	
	public abstract void executeLogic(RenderWorldLastEvent e);
	public abstract void executeRender(RenderWorldLastEvent e);
	
	public ItemStack getSkullTXZ(double originX, double originZ, double tx, double y, double tz) {
		double[] xz = this.translatedCoordinatesD(originX, originZ, tx, tz);
		return getSkull(xz[0], y, xz[1]);
	}
	
	public ItemStack getSkull(double x, double y, double z) {
		
		x += 0.5;
		z += 0.5;
		y -= 1.46875;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		EntityArmorStand armorStand = null;
		double shortestDist = -1;
		
		for (Entity entity : p.world.getLoadedEntityList()) {
			
			if (!(entity instanceof EntityArmorStand)) continue;
			
			if (entity.isDead) continue;
			
			double dist = entity.getDistance(x, y, z);
			if (shortestDist == -1 || dist < shortestDist) {
				shortestDist = dist;
				armorStand = (EntityArmorStand)entity;
			}
			
		}
		
		if (armorStand == null) return null;
		
		ItemStack skull = null;
		
		for (ItemStack stack : armorStand.getArmorInventoryList()) {
			
			Item item = stack.getItem();
			
			if (!(item instanceof ItemSkull)) continue;
			
			skull = stack;
			break;

		}
		
		return skull;
		
	}
	
	public void renderSkullTXZ(ItemStack skull, double originX, double originZ, double tx, double y, double tz, double ticks, boolean transparent) {
		double[] xz = this.translatedCoordinatesD(originX, originZ, tx, tz);
		renderSkull(skull, xz[0], y, xz[1], ticks, transparent);
	}
	
	public void renderSkull(ItemStack skull, double x, double y, double z, double ticks, boolean transparent) {
		
		Item item = skull.getItem();
		
		if (!(item instanceof ItemSkull)) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		x -= (p.prevPosX + (p.posX - p.prevPosX) * ticks);
		y -= (p.prevPosY + (p.posY - p.prevPosY) * ticks);
		z -= (p.prevPosZ + (p.posZ - p.prevPosZ) * ticks);
		
		switch (this.direction) {
		case POS_Z:
			z -= 0.25;
			break;
		case NEG_Z:
			z += 0.25;
			break;
		case POS_X:
			x -= 0.25;
			break;
		case NEG_X:
			x += 0.25;
			break;
		}
		
        GameProfile gameprofile = null;

        if (skull.hasTagCompound()) {
            NBTTagCompound nbttagcompound = skull.getTagCompound();

            if (nbttagcompound.hasKey("SkullOwner", 10)) {
                gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
            } else if (nbttagcompound.hasKey("SkullOwner", 8)) {
                String s = nbttagcompound.getString("SkullOwner");

                if (!StringUtils.isBlank(s)) {
                    gameprofile = TileEntitySkull.updateGameprofile(new GameProfile((UUID)null, s));
                    nbttagcompound.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
                }
            }
        }
		
		if (transparent) TileEntitySkullRenderer.instance.renderSkull((float)x, (float)y, (float)z, this.getFacing(this.direction), 0, skull.getMetadata(), gameprofile, -1, 0);
		else this.renderSkullFinal((float)x, (float)y, (float)z, this.getFacing(this.direction), 0, skull.getMetadata(), gameprofile, -1, 0);
		
	}
	
    private void renderSkullFinal(float x, float y, float z, EnumFacing facing, float rotationIn, int skullType, @Nullable GameProfile profile, int destroyStage, float animateTicks)
    {
        ModelBase modelbase = new ModelHumanoidHead();

        ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();

        if (profile != null)
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);

            if (map.containsKey(Type.SKIN))
            {
                resourcelocation = minecraft.getSkinManager().loadSkin(map.get(Type.SKIN), Type.SKIN);
            }
            else
            {
                UUID uuid = EntityPlayer.getUUID(profile);
                resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
            }
        }

        TileEntityRendererDispatcher.instance.renderEngine.bindTexture(resourcelocation);

        GlStateManager.pushMatrix();
        GlStateManager.disableCull();

        switch (facing)
        {
            case NORTH:
                GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.74F);
                break;
            case SOUTH:
                GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.26F);
                rotationIn = 180.0F;
                break;
            case WEST:
                GlStateManager.translate(x + 0.74F, y + 0.25F, z + 0.5F);
                rotationIn = 270.0F;
                break;
            case EAST:
            default:
                GlStateManager.translate(x + 0.26F, y + 0.25F, z + 0.5F);
                rotationIn = 90.0F;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();

        modelbase.render((Entity)null, animateTicks, 0.0F, 0.0F, rotationIn, 0.0F, 0.0625F);
        GlStateManager.popMatrix();

    }
	
	public int[] translatedCoordinatesI(int tx, int tz, int xTranslation, int zTranslation) {
		
		switch (this.direction) {
		case POS_Z:
			tx -= xTranslation;
			tz += zTranslation;
			break;
		case NEG_Z:
			tx += xTranslation;
			tz -= zTranslation;
			break;
		case POS_X:
			tx += zTranslation;
			tz += xTranslation;
			break;
		case NEG_X:
			tx -= zTranslation;
			tz -= xTranslation;
			break;
		}
		
		return new int[] {tx, tz};
		
	}
	
	public double[] translatedCoordinatesD(double tx, double tz, double xTranslation, double zTranslation) {
		
		switch (this.direction) {
		case POS_Z:
			tx -= xTranslation;
			tz += zTranslation;
			break;
		case NEG_Z:
			tx += xTranslation;
			tz -= zTranslation;
			break;
		case POS_X:
			tx += zTranslation;
			tz += xTranslation;
			break;
		case NEG_X:
			tx -= zTranslation;
			tz -= xTranslation;
			break;
		}
		
		return new double[] {tx, tz};
		
	}
	
	public double[] translatedCoordinatesInvD(double ox, double oz, double tx, double tz) {
		
		double x = 0;
		double z = 0;
		
		switch (this.direction) {
		case POS_Z:
			x = ox - tx;
			z = tz - oz;
			break;
		case NEG_Z:
			x = tx - ox;
			z = oz - tz;
			break;
		case POS_X:
			x = tz - oz;
			z = tx - ox;
			break;
		case NEG_X:
			x = oz - tz;
			z = ox - tx;
			break;
		}
		
		return new double[] {x, z};
		
	}
	
	public EnumFacing getFacing(int direction) {
		
		switch (direction) {
		case POS_Z:
			return EnumFacing.NORTH;
		case NEG_Z:
			return EnumFacing.SOUTH;
		case POS_X:
			return EnumFacing.WEST;
		case NEG_X:
			return EnumFacing.EAST;
		}
		
		return EnumFacing.NORTH;
		
	}
	
	public void render3Dtext(String text, double x, double y, double z, double scale, float ticks) {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		x -= (p.prevPosX + (p.posX - p.prevPosX) * ticks);
		y -= (p.prevPosY + (p.posY - p.prevPosY) * ticks);
		z -= (p.prevPosZ + (p.posZ - p.prevPosZ) * ticks);
		
		float rotation = 180;
		switch (this.direction) {
		case NEG_X:
			rotation = 90;
			break;
		case NEG_Z:
			rotation = 0;
			break;
		case POS_X:
			rotation = 270;
		}
		
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(scale, -scale, -scale);
        GlStateManager.disableLighting();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        fr.drawString(text, -fr.getStringWidth(text) / 2, 0, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
		
	}
	
}
