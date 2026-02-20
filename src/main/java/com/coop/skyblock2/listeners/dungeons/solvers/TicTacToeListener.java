package com.coop.skyblock2.listeners.dungeons.solvers;

import org.lwjgl.opengl.GL11;

import com.coop.skyblock2.utils.Debug;
import com.coop.skyblock2.utils.MagicRenderer;
import com.coop.skyblock2.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class TicTacToeListener {

	private static final int UNKNOWN = 0;
	private static final int POS_Z = 1;
	private static final int NEG_Z = 2;
	private static final int POS_X = 3;
	private static final int NEG_X = 4;
	
	private static int direction = UNKNOWN;
	private static int originX = -1;
	private static int originZ = -1;
	
	private static final int BLANK = 0;
	private static final int X = 30876;
	private static final int O = 30877;
	
	private static int[] slots = {0, 0, 0, 0, 0, 0, 0, 0, 0};
	private static final int[] CORNERS = {0, 2, 6, 8};
	private static final int[] EDGES = {1, 3, 5, 7};
	
	private static float ticks = 0;
	
	@SubscribeEvent
	public void calibrateRoom(PlayerTickEvent e) {
		
		if (!(e.player instanceof EntityPlayerSP)) return;
		if (direction != UNKNOWN) return;
		if (!Utils.isInDungeons()) return;
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		EntityItemFrame frame = null;
		
		for (Entity entity : p.world.loadedEntityList) {
			
			if (!(entity instanceof EntityItemFrame)) continue;
			
			if (entity.posY < 70 || entity.posY >= 73) continue;
			if (Math.abs(entity.posX - p.posX) > 5) continue;
			if (Math.abs(entity.posZ - p.posZ) > 5) continue;
			
			EntityItemFrame currentFrame = (EntityItemFrame)entity;
			
			ItemStack stack = currentFrame.getDisplayedItem();
			
			if (stack.getItem() != Items.FILLED_MAP) continue;
			
			int data = stack.getMetadata();
			
			Debug.sendDungeonsDebug("map id: " + data);
			Debug.sendDungeonsDebug("map xz: (" + entity.posX + ", " + entity.posZ + ")");
			if (data != X && data != O) continue;
			
			frame = currentFrame;
			
		}
		
		if (frame == null) return;
		
		int x = (int)frame.posX;
		int y = (int)frame.posY;
		int z = (int)frame.posZ;
		
		if (x < 0) x--;
		if (z < 0) z--;
		
		if (p.world.getBlockState(new BlockPos(x - 1, y, z)).getBlock() == Blocks.IRON_BLOCK) {
			
			direction = NEG_X;
			
		} else if (p.world.getBlockState(new BlockPos(x + 1, y, z)).getBlock() == Blocks.IRON_BLOCK) {
			
			direction = POS_X;
			
		} else if (p.world.getBlockState(new BlockPos(x, y, z - 1)).getBlock() == Blocks.IRON_BLOCK) {
			
			direction = NEG_Z;
			
		} else direction = POS_Z;
		
		Debug.sendDungeonsDebug("direction: " + direction);
		
		while (p.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STONE) {
			int[] xz = translatedCoordinates(x, z, -1, 0);
			x = xz[0];
			z = xz[1];
		}
		
		int[] xz = translatedCoordinates(x, z, 1, 0);
		originX = xz[0];
		originZ = xz[1];
		
		Debug.sendDungeonsDebug("origin: (" + originX + ", " + originZ + ")");
		
	}
	
	@SubscribeEvent
	public void drawSolution(RenderWorldLastEvent e) {
		
		if (direction == UNKNOWN) return;
		ticks = e.getPartialTicks();
		
		int turn = 0;
		
		for (int i = 0; i < 9; i++) {
			if (getSlot(i) != BLANK) turn++;
		}
		
		if (turn == 9) return;
		
		if (turn % 2 == 0) return;
		
		if (turn == 1) {
			highlightSlot(getSlot(4) == BLANK ? 4 : 0);
			return;
		}
		
		if (block3inARow()) return;
		if (getSlot(4) == X ? cornerLogic() : centerLogic()) return;
		
		for (int i = 0; i < 9; i++) {
			if (slots[i] == BLANK) {
				highlightSlot(i);
				return;
			}
		}
		
	}
	
	private static boolean cornerLogic() {
		
		for (int corner : CORNERS) {
			if (getSlot(corner) == BLANK) {
				highlightSlot(corner);
				return true;
			}
		}
		
		return false;
		
	}
	
	private static boolean centerLogic() {
		
		if (getSlot(0) == X && getSlot(8) == BLANK) {
			highlightSlot(8);
			return true;
		}
		
		if (getSlot(2) == X && getSlot(6) == BLANK) {
			highlightSlot(6);
			return true;
		}
		
		if (getSlot(6) == X && getSlot(2) == BLANK) {
			highlightSlot(2);
			return true;
		}
		
		if (getSlot(8) == X && getSlot(0) == BLANK) {
			highlightSlot(0);
			return true;
		}
		
		for (int edge : EDGES) {
			if (getSlot(edge) == BLANK) {
				highlightSlot(edge);
				return true;
			}
		}
		
		return false;
		
	}
	
	private static boolean block3inARow() {
		
		for (int i = 0; i < 3; i++) {
		
			if (getSlot(0 + (3 * i)) == X && getSlot(1 + (3 * i)) == X && getSlot(2 + (3 * i)) == BLANK) {
				highlightSlot(2 + (3 * i));
				return true;
			}
			
			if (getSlot(0 + (3 * i)) == X && getSlot(1 + (3 * i)) == BLANK && getSlot(2 + (3 * i)) == X) {
				highlightSlot(1 + (3 * i));
				return true;
			}
			
			if (getSlot(0 + (3 * i)) == BLANK && getSlot(1 + (3 * i)) == X && getSlot(2 + (3 * i)) == X) {
				highlightSlot(0 + (3 * i));
				return true;
			}
			
			if (getSlot(0 + i) == X && getSlot(3 + i) == X && getSlot(6 + i) == BLANK) {
				highlightSlot(6 + i);
				return true;
			}
			
			if (getSlot(0 + i) == X && getSlot(3 + i) == BLANK && getSlot(6 + i) == X) {
				highlightSlot(3 + i);
				return true;
			}
			
			if (getSlot(0 + i) == BLANK && getSlot(3 + i) == X && getSlot(6 + i) == X) {
				highlightSlot(0 + i);
				return true;
			}
		
		}
		
		if (getSlot(0) == X && getSlot(4) == X && getSlot(8) == BLANK) {
			highlightSlot(8);
			return true;
		}
		
		if (getSlot(0) == X && getSlot(4) == BLANK && getSlot(8) == X) {
			highlightSlot(4);
			return true;
		}
		
		if (getSlot(0) == BLANK && getSlot(4) == X && getSlot(8) == X) {
			highlightSlot(0);
			return true;
		}
		
		if (getSlot(2) == X && getSlot(4) == X && getSlot(6) == BLANK) {
			highlightSlot(6);
			return true;
		}
		
		if (getSlot(2) == X && getSlot(4) == BLANK && getSlot(6) == X) {
			highlightSlot(4);
			return true;
		}
		
		if (getSlot(2) == BLANK && getSlot(4) == X && getSlot(6) == X) {
			highlightSlot(2);
			return true;
		}
		
		return false;
		
	}
	
	private static void highlightSlot(int slot) {
		
		int tx = slot % 3;
		int y = 70 + (slot / 3);
		
		int[] xz = translatedCoordinates(originX, originZ, tx, 0);
		
		int x = xz[0];
		int z = xz[1];
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		if (Math.abs(p.posX - x) > 10) return;
		if (Math.abs(p.posY - y) > 10) return;
		if (Math.abs(p.posZ - z) > 10) return;
		
		MagicRenderer renderer = new MagicRenderer();
		renderer.autoTranslate(ticks);
		
		renderer.lineWidth(3);
		renderer.color(0, 255, 0);
		renderer.disableDepth();
		
		renderer.begin(GL11.GL_LINES);
		
		renderer.vc(x, y, z);
		renderer.vc(x + 1, y, z);
		
		renderer.vc(x + 1, y, z);
		renderer.vc(x + 1, y, z + 1);
		
		renderer.vc(x + 1, y, z + 1);
		renderer.vc(x, y, z + 1);
		
		renderer.vc(x, y, z + 1);
		renderer.vc(x, y, z);
		
		renderer.vc(x, y + 1, z);
		renderer.vc(x + 1, y + 1, z);
		
		renderer.vc(x + 1, y + 1, z);
		renderer.vc(x + 1, y + 1, z + 1);
		
		renderer.vc(x + 1, y + 1, z + 1);
		renderer.vc(x, y + 1, z + 1);
		
		renderer.vc(x, y + 1, z + 1);
		renderer.vc(x, y + 1, z);
		
		renderer.vc(x, y, z);
		renderer.vc(x, y + 1, z);
		
		renderer.vc(x + 1, y, z);
		renderer.vc(x + 1, y + 1, z);
		
		renderer.vc(x + 1, y, z + 1);
		renderer.vc(x + 1, y + 1, z + 1);
		
		renderer.vc(x, y, z + 1);
		renderer.vc(x, y + 1, z + 1);
		
		renderer.draw();
		renderer.enableDepth();
		renderer.end();
		
	}
	
	private static int getSlot(int slot) {
		
		if (slots[slot] != BLANK) return slots[slot];
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		int tx = slot % 3;
		int y = 70 + (slot / 3);
		
		int[] xz = translatedCoordinates(originX, originZ, tx, 0);
		
		if (p.world.getBlockState(new BlockPos(xz[0], y, xz[1])).getBlock() == Blocks.STONE_BUTTON) return BLANK;
		
		for (Entity entity : p.world.loadedEntityList) {
			
			if (!(entity instanceof EntityItemFrame)) continue;
			
			int ix = (int)entity.posX;
			int iy = (int)entity.posY;
			int iz = (int)entity.posZ;
			
			if (ix < 0) ix--;
			if (iz < 0) iz--;
			
			if (ix != xz[0] || iy != y || iz != xz[1]) continue;
			
			EntityItemFrame currentFrame = (EntityItemFrame)entity;
			
			slots[slot] = currentFrame.getDisplayedItem().getMetadata();
			
			return slots[slot];
			
		}
		
		return BLANK;
		
	}
	
	private static int[] translatedCoordinates(int tx, int tz, int xTranslation, int zTranslation) {
		
		switch (direction) {
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
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load e) {
		reset();
	}
	
	public static void reset() {
		
		direction = UNKNOWN;
		originX = -1;
		originZ = -1;
		
		slots = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		
	}
	
}
