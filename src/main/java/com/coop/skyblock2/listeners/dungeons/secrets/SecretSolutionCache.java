package com.coop.skyblock2.listeners.dungeons.secrets;

import java.util.HashMap;

import com.coop.skyblock2.listeners.dungeons.secrets.identifiers.SecretBat;
import com.coop.skyblock2.listeners.dungeons.secrets.identifiers.SecretChest;
import com.coop.skyblock2.listeners.dungeons.secrets.identifiers.SecretLever;
import com.coop.skyblock2.listeners.dungeons.secrets.identifiers.SecretMisc;
import com.coop.skyblock2.listeners.dungeons.secrets.identifiers.SecretWall;

public class SecretSolutionCache {

	public static HashMap<String, SecretSolution> solutions = new HashMap<>();
	public static HashMap<String, String> redirects = new HashMap<>();
	
	public static boolean solutionExists(String roomcode) {
		return solutions.containsKey(roomcode);
	}
	
	public static boolean redirectExists(String roomcode) {
		return redirects.containsKey(roomcode);
	}
	
	public static SecretSolution getSolution(String roomcode) {
		return solutions.get(roomcode);
	}
	
	public static String getRedirect(String roomcode) {
		return redirects.get(roomcode);
	}
	
	static {
		
		registerAll();
		
	}
	
	public static void registerAll() {
		
		solutions.clear();
		redirects.clear();
		
		registerS(); // 1x1 rooms
		registerD(); // 2x1 rooms
		registerT(); // 3x1 rooms
		registerQ(); // 4x1 rooms
		registerB(); // 2x2 rooms
		registerL(); // L shaped rooms
		
	}
	
	private static void registerS() {
		
		SecretSolution solution0 = new SecretSolution();
		solution0.addSecret(new SecretWall(14, 79, -4));
		solution0.addPathPoint(11, 70, 2);
		solution0.addPathPoint(19, 77, 2);
		solution0.addSecret(new SecretLever(11, 79, -4));
		solution0.addSecret(new SecretChest(10, 78, 6));
		solution0.addPathPoint(19, 79, 16);
		solution0.addPathPoint(8, 79, 13);
		solution0.addSecret(new SecretChest(-6, 78, -2));
		solutions.put("424401895146850120431408512043126151204312615137614085137311335511801148251231325912319971231523123126151231408512314239518281423951883114810110271", solution0);
		
		SecretSolution solution1 = new SecretSolution();
		solution1.addSecret(new SecretWall(2, 70, -3));
		solution1.addSecret(new SecretMisc(-5, 69, -4));
		solution1.addSecret(new SecretLever(7, 81, 19));
		solution1.addSecret(new SecretMisc(11, 87, -3));
		solution1.addSecret(new SecretChest(7, 79, -2));
		solution1.addPathPoint(17, 69, 17);
		solution1.addPathPoint(16, 80, -3);
		solutions.put("6031171701117150614818981211911516134211581171265115111017512161751200114913201271112016051821691100151182126915751491", solution1);
		
		SecretSolution solution2 = new SecretSolution();
		solution2.addSecret(new SecretChest(12, 76, 15));
		solution2.addPathPoint(20, 71, 11);
		solutions.put("302872301103151187137515151100136614161477148115721211379122142019715721797517414864130122318712280155814711237216861", solution2);
		
		SecretSolution solution3 = new SecretSolution();
		solution3.addSecret(new SecretWall(3, 70, -2));
		solution3.addSecret(new SecretChest(-5, 69, 2));
		solution3.addSecret(new SecretChest(5, 86, 6));
		solution3.addPathPoint(15, 85, 7);
		solutions.put("11407951891501670412109150168021264515016802175150168021751309127112381-839350286115117311417531121751112111317512458274981151245827498175124582740017511131151", solution3);
		
		SecretSolution solution4 = new SecretSolution();
		solution4.addSecret(new SecretWall(10, 70, 12));
		solution4.addSecret(new SecretMisc(12, 69, 15));
		solutions.put("11911120311191112031109311203117211159811434113521201167412911811674127261381167421311113931159811844111911110511191190911191190911191110071", solution4);
		
		SecretSolution solution5 = new SecretSolution();
		solution5.addSecret(new SecretWall(3, 70, 18));
		solution5.addSecret(new SecretChest(-4, 70, 17));
		solutions.put("2151743601615121517436018031215174360170914111611115411921272902831231150287127115426151411170911437931101803121517436018081215174360160618031", solution5);
		
		SecretSolution solution6 = new SecretSolution();
		solution6.addSecret(new SecretWall(-3, 70, 7));
		solution6.addSecret(new SecretLever(-5, 70, 2));
		solution6.addSecret(new SecretChest(17, 81, 20));
		solution6.addPathPoint(10, 84, 2);
		solution6.addSecret(new SecretChest(7, 82, 20));
		solution6.addSecret(new SecretWall(17, 70, 0));
		solution6.addSecret(new SecretChest(15, 62, 10));
		solutions.put("6171157212251611112814191225111781519110031421181218181103411033151119341819122518481225110281325119651349161115201782142511453126441717110111", solution6);
		
		SecretSolution solution7 = new SecretSolution();
		solution7.addSecret(new SecretChest(-4, 83, -1));
		solutions.put("2211812211818611818711818811818911912611912611912614081261144190114414254717441948846811931142328467188114232840418811423284041881", solution7);
		
		SecretSolution solution8 = new SecretSolution();
		solution8.addSecret(new SecretChest(-6, 82, 17));
		solution8.addSecret(new SecretMisc(14, 82, 4));
		solutions.put("75311031121111751151171151175175113911751139111511751151175115177175151817511141151171840117512411413139911113193419741", solution8);
		
		SecretSolution solution9 = new SecretSolution();
		solution9.addSecret(new SecretBat(-5, 64, 19));
		solution9.addPathPoint(-5, 64, 15);
		solution9.addSecret(new SecretChest(7, 54, 6));
		solution9.addPathPoint(7, 64, -4);
		solution9.addPathPoint(-4, 58, -4);
		solution9.addPathPoint(-4, 54, 8);
		solution9.addSecret(new SecretMisc(-6, 78, 2));
		solutions.put("6096156111886198319841278412778110821790110821456121013083211121013081112121011124141111221411123141", solution9);
		
		SecretSolution solution10 = new SecretSolution();
		solution10.addSecret(new SecretChest(16, 77, -2));
		solution10.addSecret(new SecretWall(13, 74, 17));
		solution10.addPathPoint(18, 77, 12);
		solution10.addSecret(new SecretChest(16, 69, 19));
		solution10.addSecret(new SecretChest(16, 69, 13));
		solution10.addSecret(new SecretLever(20, 70, 16));
		solution10.addSecret(new SecretChest(0, 84, 18));
		solution10.addPathPoint(-2, 82, 3);
		solution10.addPathPoint(-4, 84, 18);
		solutions.put("18145356011191453560111914761191141191141505114115539912311700301401144061272364293311527871363016941506179140811000501", solution10);
		
		SecretSolution solution11 = new SecretSolution();
		solution11.addSecret(new SecretMisc(21, 77, 20));
		solution11.addPathPoint(8, 76, -4);
		solution11.addPathPoint(18, 77, -4);
		solutions.put("128821214112882-1880284174112881-109507070317981-109507070317981-1095070703162613168875417981112179811121128811121128811121128811121128811121128818431128812101128812101", solution11);
		
		SecretSolution solution12 = new SecretSolution();
		solution12.addSecret(new SecretWall(12, 70, -1));
		solution12.addSecret(new SecretMisc(14, 69, 1));
		solutions.put("87117918718214766313771178280258818731221791221791222177112018021782284651277", solution12);
		
		SecretSolution solution13 = new SecretSolution();
		solution13.addSecret(new SecretMisc(16, 77, 18));
		solution13.addPathPoint(21, 77, 9);
		solutions.put("32131442411413211377196818138129242112614905654612913212913213113214481294270094129110615401145129114521661145123593011451211506164182461482101787981814011451831", solution13);
		
		SecretSolution solution14 = new SecretSolution();
		solution14.addSecret(new SecretWall(12, 71, -5));
		solution14.addSecret(new SecretMisc(20, 69, -7));
		solution14.addSecret(new SecretMisc(14, 69, -2));
		solutions.put("27170612717061271706127175592912712133112712715746912718231271639938471271120127111812712931271421271138127153211271391272", solution14);
		
		SecretSolution solution15 = new SecretSolution();
		solution15.addSecret(new SecretLever(16, 85, -6));
		solution15.addSecret(new SecretMisc(-6, 98, -3));
		solution15.addSecret(new SecretChest(15, 58, 3));
		solutions.put("11124028113146863921151367911401171140116117116115521856118101154113938115441154011515115311111264014711401150431141381261141352859811414311", solution15);
		
		SecretSolution solution16 = new SecretSolution();
		solution16.addSecret(new SecretMisc(17, 66, 17));
		solutions.put("48114121481822148113414811180148118014811801481504148121014811731-129129147413801-129129147412101", solution16);
	
		SecretSolution solution17 = new SecretSolution();
		solution17.addSecret(new SecretWall(7, 82, 7));
		solution17.addPathPoint(5, 69, 17);
		solution17.addPathPoint(-2, 72, 17);
		solution17.addPathPoint(-2, 80, 7);
		solution17.addSecret(new SecretChest(19, 81, 6));
		solutions.put("21912219031704180515111831110261312121412613121201217120111612312171121112011241318182919291609190613618291361214128420613151", solution17);
		
		SecretSolution solution18 = new SecretSolution();
		solution18.addSecret(new SecretWall(1, 70, 17));
		solution18.addSecret(new SecretChest(-6, 69, 17));
		solution18.addSecret(new SecretLever(8, 62, 10));
		solution18.addPathPoint(17, 66, 17);
		solution18.addPathPoint(16, 62, 7);
		solution18.addSecret(new SecretChest(17, 76, 21));
		solution18.addPathPoint(16, 64, -2);
		solution18.addPathPoint(-2, 69, 4);
		solution18.addPathPoint(4, 76, 7);
		solution18.addPathPoint(17, 76, 8);
		solution18.addSecret(new SecretChest(15, 84, -3));
		solution18.addPathPoint(15, 79, -5);
		solutions.put("241145512411188136312499813510164117021843515151328127751106516711374161311065182716731257161115911065182113517141351617163911038152011561", solution18);
		
		SecretSolution solution19 = new SecretSolution();
		solution19.addSecret(new SecretChest(7, 72, 19));
		solution19.addPathPoint(7, 71, 11);
		solutions.put("2726891591168224601682185112212812222117832231181682119912161141514791141212717061274", solution19);
		
		SecretSolution solution20 = new SecretSolution();
		solution20.addSecret(new SecretMisc(11, 76, 3));
		solution20.addSecret(new SecretChest(7, 74, 21));
		solutions.put("3849091516125118711221169123112511141315111412171171210110512631114121011913151371217116312771382301381", solution20);
		
		SecretSolution solution21 = new SecretSolution();
		solution21.addSecret(new SecretMisc(-2, 69, 13));
		solution21.addPathPoint(2, 74, 6);
		solution21.addSecret(new SecretMisc(16, 80, 13));
		solution21.addPathPoint(10, 78, 16);
		solutions.put("271162681271162412717744141415750923138614521178131712151390612151374111813151415111714151502141511931519513121553770145111682114101166514101", solution21);
		
		SecretSolution solution22 = new SecretSolution();
		solution22.addSecret(new SecretMisc(13, 75, -3));
		solution22.addSecret(new SecretMisc(18, 75, -3));
		solutions.put("2340157110291100012716117849110931109456178111313165111317521531731151211175116321751218811511223017511265517511265511731321612711", solution22);
		
		SecretSolution solution23 = new SecretSolution();
		solution23.addSecret(new SecretMisc(14, 89, 10));
		solution23.addPathPoint(3, 84, -2);
		solution23.addPathPoint(16, 87, -4);
		solutions.put("1000124180111251802124115194115112511811891151271151221118121511513851151318118132311715141181518115141711714871", solution23);
		
		SecretSolution solution24 = new SecretSolution();
		solution24.addSecret(new SecretMisc(16, 92, 1));
		solution24.addPathPoint(15, 81, 7);
		solution24.addSecret(new SecretChest(-6, 81, -1));
		solution24.addPathPoint(15, 81, 7);
		solutions.put("11811711181115111812781409150713111214121128511361105117211701118158011181580169612050131113231171116117183111214061171877381", solution24);
		
		SecretSolution solution25 = new SecretSolution();
		solution25.addSecret(new SecretWall(9, 55, 3));
		solution25.addPathPoint(7, 60, -4);
		solution25.addPathPoint(-1, 58, -3);
		solution25.addPathPoint(0, 53, 17);
		solution25.addPathPoint(9, 53, 17);
		solution25.addSecret(new SecretChest(9, 55, -5));
		solutions.put("1751661134511631366111813030111511157212111106251170121281140116170171102311200123211113123581595121011231210111513715012341", solution25);
		
		SecretSolution solution26 = new SecretSolution();
		solution26.addSecret(new SecretBat(-1, 71, -4));
		solution26.addSecret(new SecretLever(1, 75, -2));
		solution26.addSecret(new SecretChest(-5, 78, -4));
		solution26.addPathPoint(18, 77, -3);
		solutions.put("17989785121179160163931785112710811271125227863932576291601723104101149522851100416601", solution26);
		
		SecretSolution solution27 = new SecretSolution();
		solution27.addSecret(new SecretMisc(4, 69, 15));
		solutions.put("7559291523313835146451309112014031215187812794172314371184161871523312441498711171215212014550112011106112012151523316391152331270551215113691", solution27);
		
		SecretSolution solution28 = new SecretSolution();
		solution28.addSecret(new SecretChest(-4, 62, 15));
		solutions.put("22412481129130711251192112711911123212013287121613901105141113212211116238113116091120161312121121311251219112612251227169113211", solution28);
		
		SecretSolution solution29 = new SecretSolution();
		solution29.addSecret(new SecretBat(-4, 70, 13));
		solution29.addSecret(new SecretLever(13, 70, 19));
		solution29.addSecret(new SecretChest(-7, 69, -3));
		solutions.put("132419041415611198121786484611100155001904110381714142614961221132812211521123013221483132215641582187216101577127371511132421511127821511127821", solution29);
		
		SecretSolution solution30 = new SecretSolution();
		solution30.addSecret(new SecretMisc(19, 81, -5));
		solution30.addPathPoint(-4, 69, -4);
		solution30.addSecret(new SecretChest(-4, 86, 12));
		solution30.addPathPoint(-4, 69, 18);
		solution30.addPathPoint(19, 85, 18);
		solution30.addPathPoint(19, 85, -4);
		solution30.addPathPoint(-4, 85, -4);
		solutions.put("152324841172504117411511711151173181171115117148117513891171-192977864411428141011121", solution30);
		
		SecretSolution solution31 = new SecretSolution();
		solution31.addSecret(new SecretLever(-1, 70, 7));
		solution31.addSecret(new SecretChest(-3, 69, -6));
		solutions.put("9160110033123521235111671120115311523116222531235112655412684812449123511384134912501179812501251134814171215612253191601100331241261", solution31);
		
		SecretSolution solution32 = new SecretSolution();
		solution32.addSecret(new SecretWall(7, 60, 1));
		solution32.addSecret(new SecretChest(7, 59, -3));
		solution32.addSecret(new SecretChest(-6, 77, 17));
		solutions.put("1016171219611109812711277130924742245461464412121338117312111271130914741145451464314839130912721211117411001160918031510145813111", solution32);
		
		SecretSolution solution33 = new SecretSolution();
		solution33.addSecret(new SecretLever(-7, 71, 17));
		solution33.addSecret(new SecretMisc(10, 71, 0));
		solution33.addSecret(new SecretMisc(12, 71, -5));
		solution33.addSecret(new SecretChest(9, 63, 17));
		solution33.addPathPoint(-3, 71, 17);
		solution33.addSecret(new SecretChest(10, 63, 15));
		solutions.put("8021428167815181906114310328613070134981611119235181615371611112299716471213216291762187014471251123133197955209018731725518731298705610156201214981562011052661140501", solution33);
		
		SecretSolution solution34 = new SecretSolution();
		solution34.addSecret(new SecretWall(5, 78, -2));
		solution34.addSecret(new SecretMisc(5, 77, -6));
		solution34.addSecret(new SecretLever(-5, 85, 7));
		solution34.addPathPoint(11, 74, 7);
		solution34.addPathPoint(12, 78, 18);
		solution34.addPathPoint(11, 83, 7);
		solution34.addSecret(new SecretMisc(-7, 61, 7));
		solution34.addPathPoint(-4, 83, -2);
		solution34.addPathPoint(-2, 66, -4);
		solution34.addPathPoint(-2, 60, 7);
		solution34.addSecret(new SecretLever(21, 61, 7));
		solution34.addSecret(new SecretChest(21, 75, 7));
		solution34.addPathPoint(-1, 61, 2);
		solution34.addPathPoint(-4, 66, -4);
		solution34.addPathPoint(-2, 69, 7);
		solutions.put("2368119761217413922115841138612643116590312624185219612040111011650203215613131956112001756841102612649111511268513570350123681129873118761129111978149971217412072120741679641", solution34);
		
		SecretSolution solution35 = new SecretSolution();
		solution35.addSecret(new SecretMisc(8, 61, 16));
		solution35.addPathPoint(5, 71, 17);
		solution35.addSecret(new SecretMisc(9, 61, 21));
		solutions.put("832482756611-15406831711169146317113555712183691711239252135511836913551713144144171515651122016501146165013013581", solution35);
		
		SecretSolution solution36 = new SecretSolution();
		solution36.addSecret(new SecretLever(12, 69, -1));
		solution36.addSecret(new SecretChest(20, 60, 10));
		solution36.addPathPoint(11, 71, 13);
		solution36.addSecret(new SecretChest(4, 61, 5));
		solutions.put("86134032471371323182151307185161912191765171113241592114712857131157311281218131115721759118871130161111531966601371281371371137134034351", solution36);
		
		SecretSolution solution37 = new SecretSolution();
		solution37.addSecret(new SecretChest(-4, 75, 21));
		solution37.addPathPoint(4, 69, -2);
		solution37.addPathPoint(-4, 75, -1);
		solutions.put("41213021217129516031171102517511025175182317518971151995115111517513001751171517512901151879017511711511711511191116011", solution37);
		
		SecretSolution solution38 = new SecretSolution();
		solution38.addSecret(new SecretWall(6, 70, 13));
		solution38.addSecret(new SecretChest(5, 69, 10));
		solutions.put("2711131271370127179127179127139512712831271583127161112711084127111091271951951212781139519512127811395127142512711723127113221", solution38);
		
		SecretSolution solution39 = new SecretSolution();
		solution39.addSecret(new SecretMisc(18, 70, 2));
		solution39.addSecret(new SecretWall(15, 70, -3));
		solution39.addSecret(new SecretLever(19, 70, -3));
		solution39.addSecret(new SecretMisc(-2, 69, 18));
		solution39.addSecret(new SecretMisc(-1, 68, 19));
		solution39.addSecret(new SecretChest(15, 62, 20));
		solution39.addPathPoint(11, 67, 7);
		solution39.addPathPoint(15, 68, 10);
		solutions.put("92121281220112812201331125133112511281220112823021284125132133131112811251128112419201912116316131", solution39);
		
		SecretSolution solution40 = new SecretSolution();
		solution40.addSecret(new SecretLever(-3, 84, -1));
		solution40.addSecret(new SecretChest(-2, 69, 7));
		solution40.addSecret(new SecretWall(10, 63, -2));
		solution40.addSecret(new SecretMisc(-6, 63, -2));
		solutions.put("1841159413841117165212151257211191145118111711814582118131511815531113119311131368144011613161311131461150111161-16364575251951-1882761331201-11497074451201", solution40);
		
		SecretSolution solution41 = new SecretSolution();
		solution41.addSecret(new SecretWall(0, 70, 7));
		solution41.addSecret(new SecretChest(-4, 69, 7));
		solution41.addSecret(new SecretMisc(19, 68, 10));
		solution41.addPathPoint(12, 71, 16);
		solutions.put("523811052120911105191513327181011111190815914111054523729121523138661", solution41);
		
		SecretSolution solution42 = new SecretSolution();
		solution42.addSecret(new SecretWall(12, 70, 16));
		solution42.addSecret(new SecretMisc(16, 69, 16));
		solution42.addSecret(new SecretMisc(-1, 65, -2));
		solution42.addSecret(new SecretLever(-4, 88, -2));
		solution42.addPathPoint(7, 65, 7);
		solution42.addPathPoint(-2, 69, 3);
		solution42.addPathPoint(15, 73, -1);
		solution42.addPathPoint(9, 80, -3);
		solution42.addSecret(new SecretChest(3, 82, 11));
		solution42.addPathPoint(15, 73, -2);
		solution42.addPathPoint(15, 79, 14);
		solutions.put("3101161180112141300411528141011571324115715071115711381611571187551187171561459125655122851381012286142701765301106011285712830172842166413847101139101139621", solution42);
		
		SecretSolution solution43 = new SecretSolution();
		solution43.addSecret(new SecretChest(2, 72, 10));
		solutions.put("2821271279127114512652839121731174712407150130113811211161254611161173234122140711291306123132111711132111879110551301331391107111391", solution43);
		
		SecretSolution solution44 = new SecretSolution();
		solution44.addSecret(new SecretWall(5, 70, -6));
		solution44.addSecret(new SecretMisc(11, 70, -2));
		solution44.addSecret(new SecretWall(11, 80, 7));
		solution44.addPathPoint(4, 78, 7);
		solution44.addSecret(new SecretChest(20, 78, 7));
		solutions.put("911123125112312517471251273125191971251302125112912512912511291251521191194481251360546125171512515151251515125157481201", solution44);
		
		SecretSolution solution45 = new SecretSolution();
		solution45.addSecret(new SecretMisc(0, 53, -7));
		solution45.addPathPoint(-1, 51, -4);
		solution45.addSecret(new SecretChest(3, 91, -4));
		solution45.addPathPoint(3, 91, 8);
		solutions.put("1812450110111270304147081423512348213111512348211408750118379110128160110112739664411011873231458110125941371101233521131173611312422110134581101895110120711811531351", solution45);
		
		SecretSolution solution46 = new SecretSolution();
		solution46.addSecret(new SecretWall(19, 82, 5));
		solution46.addPathPoint(7, 81, 10);
		solution46.addSecret(new SecretChest(19, 82, -2));
		solution46.addSecret(new SecretMisc(-2, 63, -6));
		solution46.addPathPoint(4, 65, -4);
		solutions.put("1055157011055511612131244140716641213156515011055163315615176711122115191161611220211232105525702105515701", solution46);
		
		SecretSolution solution47 = new SecretSolution();
		solution47.addSecret(new SecretWall(11, 70, 7));
		solution47.addSecret(new SecretLever(15, 70, 7));
		solution47.addSecret(new SecretChest(-4, 69, 6));
		solutions.put("8726727574118214761182322511821280118215741476157426725874", solution47);
		
		SecretSolution solution48 = new SecretSolution();
		solution48.addSecret(new SecretMisc(-5, 60, 1));
		solution48.addPathPoint(-3, 69, 3);
		solution48.addPathPoint(0, 64, -4);
		solution48.addPathPoint(-1, 60, 4);
		solution48.addSecret(new SecretChest(19, 80, -5));
		solution48.addSecret(new SecretChest(-3, 80, -6));
		solution48.addPathPoint(-1, 79, 7);
		solutions.put("524491136831143312234618021130901509111222110924511518291394140001155432583170313221352517011162676445174513174185717991147787381380412515237581100216501911100311220475414497112173110321280911527611071103081", solution48);
		
		SecretSolution solution49 = new SecretSolution();
		solution49.addSecret(new SecretWall(7, 70, -1));
		solution49.addSecret(new SecretLever(16, 88, 19));
		solution49.addPathPoint(7, 71, -5);
		solution49.addPathPoint(-1, 76, -4);
		solution49.addPathPoint(20, 85, 19);
		solution49.addSecret(new SecretChest(16, 69, 3));
		solutions.put("171800117119512091212127712941577131011261407123751980151511161851951405187511781951178122180251612212612141288894941121122012191", solution49);
		
		SecretSolution solution50 = new SecretSolution();
		solution50.addSecret(new SecretWall(14, 70, -2));
		solution50.addSecret(new SecretChest(13, 69, -5));
		solution50.addSecret(new SecretWall(16, 70, -5));
		solution50.addSecret(new SecretMisc(18, 71, -4));
		solution50.addSecret(new SecretMisc(-4, 72, -2));
		solution50.addPathPoint(7, 69, 2);
		solution50.addSecret(new SecretLever(7, 68, 7));
		solution50.addSecret(new SecretChest(-7, 70, 7));
		solutions.put("8612418612412312417761122141412201416112313191241318124122012012191841516122018081123123124114012312251951232", solution50);
		
		SecretSolution solution51 = new SecretSolution();
		solution51.addSecret(new SecretWall(-2, 66, 16));
		solution51.addSecret(new SecretChest(-5, 66, 20));
		solution51.addSecret(new SecretMisc(-6, 75, 14));
		solution51.addPathPoint(4, 73, 9);
		solution51.addSecret(new SecretMisc(19, 78, 3));
		solutions.put("411266123180712311391235941231188123190123191123194123245712191132111028192911127127941840112912891", solution51);
		
		SecretSolution solution52 = new SecretSolution();
		solution52.addSecret(new SecretChest(13, 78, 15));
		solution52.addPathPoint(19, 77, 10);
		solution52.addSecret(new SecretLever(-3, 84, 20));
		solution52.addSecret(new SecretChest(21, 70, 16));
		solution52.addPathPoint(15, 69, 10);
		solutions.put("66120122177131512976179122051791116117311816217516211817516211816217515541751674175126511811261181761751761", solution52);
		
		SecretSolution solution53 = new SecretSolution();
		solution53.addSecret(new SecretChest(-6, 69, 19));
		solution53.addSecret(new SecretLever(-3, 65, 7));
		solution53.addSecret(new SecretChest(-5, 80, 7));
		solution53.addPathPoint(12, 69, -4);
		solution53.addPathPoint(-5, 76, -4);
		solution53.addSecret(new SecretBat(5, 82, 18));
		solutions.put("31331804123651999120551645013325167218701336015851830778118017511124186011801654166416201781179176812151672141111097150915000186318041428053519021", solution53);
		
		SecretSolution solution54 = new SecretSolution();
		solution54.addSecret(new SecretWall(7, 75, 17));
		solution54.addSecret(new SecretLever(7, 75, 21));
		solution54.addSecret(new SecretChest(7, 66, -2));
		solution54.addPathPoint(0, 67, 17);
		solution54.addPathPoint(7, 66, 17);
		solutions.put("321301321301441451320141712222931022211251320122214514219513311671331251331", solution54);
		
		SecretSolution solution55 = new SecretSolution();
		solution55.addSecret(new SecretChest(7, 85, 7));
		solutions.put("91211106110081714111061715181311105171517131417141912450132112231124132111261419137491814181329091100911106181211008211051911110091", solution55);
		
		SecretSolution solution56 = new SecretSolution();
		solution56.addSecret(new SecretChest(0, 85, -5));
		solution56.addPathPoint(0, 73, 15);
		solution56.addPathPoint(0, 84, 5);
		solutions.put("900111719951166199514171897170018971141721751181811115114118115118115118114811161751181631181799118121411812771", solution56);
		
		SecretSolution solution57 = new SecretSolution();
		solution57.addSecret(new SecretChest(8, 75, 19));
		solution57.addPathPoint(3, 75, 21);
		solution57.addSecret(new SecretMisc(15, 74, 19));
		solution57.addSecret(new SecretWall(5, 91, 16));
		solution57.addPathPoint(-1, 74, 16);
		solution57.addPathPoint(-5, 89, 17);
		solution57.addSecret(new SecretBat(13, 94, 14));
		solutions.put("2561100271403157501188312812938122213013491301701192170143513239123347181174122297411104134236012511160110115931123231159311363515253391239515785561562513409021", solution57);
		
		SecretSolution solution58 = new SecretSolution();
		solution58.addSecret(new SecretBat(-5, 64, -4));
		solution58.addSecret(new SecretChest(8, 54, 7));
		solution58.addPathPoint(18, 64, 7);
		solution58.addPathPoint(18, 59, -4);
		solution58.addPathPoint(8, 54, -4);
		solution58.addSecret(new SecretMisc(12, 78, -5));
		solutions.put("441129811118129668118114207461931778201441177856144013197412591249541114131974125913193819312851814411708901931743101181105757816614279819312690114411210131", solution58);
		
		SecretSolution solution59 = new SecretSolution();
		solution59.addSecret(new SecretChest(19, 70, 15));
		solutions.put("351181321211351181902117170911819961241518118169912113391391709157151511831905111611271607122412211281311281361", solution59);
		
		SecretSolution solution60 = new SecretSolution();
		solution60.addSecret(new SecretBat(-5, 74, 2));
		solutions.put("3972143081177611494113741225011398118721397215436245601228012538126581175812658121601543614560139721543611398114941137412610113981149413972154363", solution60);
		
		SecretSolution solution61 = new SecretSolution();
		solution61.addSecret(new SecretChest(17, 70, -3));
		solution61.addSecret(new SecretChest(2, 60, 11));
		solution61.addPathPoint(-2, 60, 16);
		solution61.addPathPoint(-2, 70, 16);
		solutions.put("351012222511221251125225112212201185135119013511381351190135114813512001351", solution61);
		
	} //tps
	
	private static void registerD() {
		
		SecretSolution solution0 = new SecretSolution();
		solution0.addSecret(new SecretWall(-29, 70, -1));
		solution0.addSecret(new SecretMisc(-36, 69, -1));
		solution0.addSecret(new SecretMisc(-9, 70, 10));
		solution0.addSecret(new SecretChest(-1, 59, -1));
		solution0.addPathPoint(-10, 69, 5);
		solution0.addPathPoint(-12, 58, 4);
		solution0.addSecret(new SecretLever(1, 83, 7));
		solution0.addPathPoint(17, 75, -1);
		solution0.addPathPoint(-1, 93, 15);
		solution0.addSecret(new SecretChest(-37, 94, 7));
		solution0.addSecret(new SecretWall(-29, 94, -1));
		solution0.addSecret(new SecretChest(-38, 94, -1));
		solution0.addSecret(new SecretWall(16, 80, 15));
		solution0.addSecret(new SecretMisc(17, 70, 16));
		solutions.put("-2194710313161151130190016091143034628150251-1196531340113811138411920519431393513535112881225351104301-1033420332156271436011601516450142291-14167893471845117111897111011361111112982112812309111", solution0);
		redirects.put("3210178351831801612125121209491454796511-6814741131181701275719239152261238379120751392540120570162331-10334207231168711796164951139211589197717190187011008143319141708501104813801", "-2194710313161151130190016091143034628150251-1196531340113811138411920519431393513535112881225351104301-1033420332156271436011601516450142291-14167893471845117111897111011361111112982112812309111");
		
		SecretSolution solution1 = new SecretSolution();
		solution1.addSecret(new SecretLever(-6, 77, 39));
		solution1.addSecret(new SecretLever(20, 88, 39));
		solution1.addSecret(new SecretLever(-5, 94, 51));
		solution1.addSecret(new SecretChest(7, 58, 23));
		solution1.addSecret(new SecretMisc(9, 57, 19));
		solution1.addSecret(new SecretChest(-6, 93, 15));
		solution1.addSecret(new SecretMisc(-5, 80, -2));
		solutions.put("125119212331124142911241614132313251269513011241301127134911431225145113012614231421171423112811291321311141161618081", solution1);
		redirects.put("6061605160217991701196514135171814574186514620179917981408179917012896179816031701170012311871231157123114151187123112112211", "125119212331124142911241614132313251269513011241301127134911431225145113012614231421171423112811291321311141161618081");
		
		SecretSolution solution2 = new SecretSolution();
		solution2.addSecret(new SecretLever(7, 62, -36));
		solution2.addSecret(new SecretChest(7, 58, -16));
		solution2.addSecret(new SecretChest(19, 83, -10));
		solution2.addSecret(new SecretWall(15, 78, 18));
		solution2.addSecret(new SecretMisc(21, 78, 18));
		solutions.put("1121210211314062211121213091406121031121113240626692120117901554511550155416071126501456111091102001226015541", solution2);
		redirects.put("2830157018511141861472157017311377173616641310112261833170201408116121833119361129501549132601644190414121621130201409120451506132914081", "1121210211314062211121213091406121031121113240626692120117901554511550155416071126501456111091102001226015541");
		
		SecretSolution solution3 = new SecretSolution();
		solution3.addSecret(new SecretChest(-1, 91, 44));
		solution3.addPathPoint(-4, 84, 43);
		solution3.addSecret(new SecretChest(7, 88, -6));
		solution3.addPathPoint(6, 94, -7);
		solutions.put("7983896170017981700112881798115564128770141865012877013359417985700179817001798214214234213612341", solution3);
		redirects.put("2342841114641841115191197439902253001187617981128814771258811092119085461163311908546112881-2652785721148416971158212231168812891158214671119015651109214051", "7983896170017981700112881798115564128770141865012877013359417985700179817001798214214234213612341");
		
		SecretSolution solution4 = new SecretSolution();
		solution4.addSecret(new SecretMisc(48, 57, 20));
		solution4.addPathPoint(40, 49, 5);
		solution4.addPathPoint(45, 51, -4);
		solution4.addPathPoint(52, 53, 15);
		solution4.addSecret(new SecretWall(34, 50, 17));
		solution4.addSecret(new SecretMisc(28, 49, 21));
		solution4.addSecret(new SecretLever(19, 55, 21));
		solution4.addSecret(new SecretChest(15, 49, 21));
		solution4.addSecret(new SecretChest(-5, 49, 16));
		solution4.addSecret(new SecretChest(22, 67, -1));
		solution4.addPathPoint(20, 62, -7);
		solutions.put("749161549815350416480136184211079016546139211159041382051340101105622621181611011867410182440101886401016158110112188766411302721224057411089818126120613563611458113771123416361", solution4);
		redirects.put("117111193189361789211169011779012519253815142181251718215116936411113101112904701511449040125178141514146111546137721414334018804781362859292129276161656018811", "749161549815350416480136184211079016546139211159041382051340101105622621181611011867410182440101886401016158110112188766411302721224057411089818126120613563611458113771123416361");
		
		SecretSolution solution5 = new SecretSolution();
		solution5.addSecret(new SecretMisc(8, 88, 7));
		solution5.addPathPoint(19, 71, 18);
		solution5.addPathPoint(19, 91, 13);
		solution5.addSecret(new SecretChest(52, 81, 8));
		solution5.addPathPoint(22, 81, 15);
		solution5.addPathPoint(33, 84, 18);
		solution5.addSecret(new SecretWall(39, 89, -1));
		solution5.addSecret(new SecretChest(35, 89, -6));
		solution5.addSecret(new SecretChest(10, 86, 18));
		solution5.addPathPoint(41, 85, 12);
		solution5.addPathPoint(16, 81, -6);
		solution5.addPathPoint(-2, 86, 4);
		solution5.addSecret(new SecretWall(21, 68, 7));
		solution5.addSecret(new SecretChest(41, 60, 7));
		solutions.put("507018316011041145151645390146601388780201171411882501703112912050121914315160314315188701225401205125451139170519725182205121719414320152951678011197148001224131021", solution5);
		redirects.put("422614169214545139508149801252011395115121271474613951119081237515590112811130244160511279718311786412712632161080145711179517264612306110067413031120971211113601", "507018316011041145151645390146601388780201171411882501703112912050121914315160314315188701225401205125451139170519725182205121719414320152951678011197148001224131021");
		
		SecretSolution solution6 = new SecretSolution();
		solution6.addSecret(new SecretWall(11, 70, -3));
		solution6.addSecret(new SecretChest(21, 70, -6));
		solution6.addSecret(new SecretMisc(13, 83, 18));
		solution6.addPathPoint(18, 69, 10);
		solution6.addPathPoint(16, 75, 19);
		solution6.addSecret(new SecretMisc(-33, 89, 4));
		solution6.addPathPoint(13, 88, 2);
		solution6.addPathPoint(-23, 88, 10);
		solutions.put("10081616117811705171317121618112031928111111130110211122135110051371911137161011931190412051190411075122921202115121201119041100119041991", solution6);
		redirects.put("6211231261406112214061353012318301231806123115012311301241261241126123115014061142145511301406112212311221231261231", "10081616117811705171317121618112031928111111130110211122135110051371911137161011931190412051190411075122921202115121201119041100119041991");

		SecretSolution solution7 = new SecretSolution();
		solution7.addSecret(new SecretLever(-32, 59, 20));
		solution7.addSecret(new SecretChest(19, 82, 7));
		solution7.addSecret(new SecretMisc(-33, 81, 9));
		solution7.addSecret(new SecretWall(-26, 94, 3));
		solution7.addPathPoint(-25, 81, -2);
		solution7.addPathPoint(-31, 93, 11);
		solution7.addSecret(new SecretBat(-26, 95, -4));
		solution7.addSecret(new SecretMisc(-31, 53, 5));
		solution7.addPathPoint(11, 56, 7);
		solution7.addPathPoint(8, 52, -4);
		solution7.addSecret(new SecretMisc(-31, 52, 9));
		solution7.addSecret(new SecretChest(-19, 52, 13));
		solutions.put("395111811592110851396112761592115701686112831784114761593148601785136841494110791496115741706115731720117651545117661780117721531117641823114751", solution7);
		redirects.put("147511084116721982116701492127741412216605149412167159213614149511269149511357159312554149416142141511084123941889192217891121716402198416122111781", "395111811592110851396112761592115701686112831784114761593148601785136841494110791496115741706115731720117651545117661780117721531117641823114751");
		
		SecretSolution solution8 = new SecretSolution();
		solution8.addSecret(new SecretChest(20, 87, -3));
		solution8.addSecret(new SecretLever(-7, 59, 42));
		solution8.addPathPoint(-4, 58, 39);
		solution8.addSecret(new SecretLever(7, 59, 35));
		solution8.addPathPoint(7, 58, 27);
		solution8.addSecret(new SecretWall(7, 59, 49));
		solution8.addSecret(new SecretLever(7, 59, 53));
		solution8.addSecret(new SecretLever(7, 59, 37));
		solution8.addSecret(new SecretBat(6, 63, 41));
		solution8.addSecret(new SecretChest(9, 58, 0));
		solution8.addPathPoint(7, 58, 19);
		solution8.addSecret(new SecretChest(5, 58, 0));
		solution8.addSecret(new SecretWall(18, 59, 30));
		solution8.addSecret(new SecretChest(9, 51, 23));
		solutions.put("18113611811036621035110401201511525152921211110351207111211035130911525143713071661360011013594110141821101661102", solution8);
		redirects.put("66159111830159113594110141821661240110401210110351110130511525130511525130512061406130811121661200110113511012621101262140716591", "18113611811036621035110401201511525152921211110351207111211035130911525143713071661360011013594110141821101661102");
		
		SecretSolution solution9 = new SecretSolution();
		solution9.addSecret(new SecretBat(0, 82, 17));
		solution9.addPathPoint(0, 75, 13);
		solution9.addSecret(new SecretMisc(-3, 82, 18));
		solution9.addSecret(new SecretChest(7, 62, 2));
		solution9.addPathPoint(7, 72, 0);
		solution9.addSecret(new SecretChest(9, 70, -24));
		solution9.addPathPoint(6, 78, -30);
		solutions.put("352120145113282117678133203212652520401347658153050408114535154906901328514260981135142001411151130125134140913513412911371100914112251130113161321341331", solution9);
		redirects.put("126111012355111013312211341110117550125128711221341221321301144157157714313213411121341114134132134112013711131401", "352120145113282117678133203212652520401347658153050408114535154906901328514260981135142001411151130125134140913513412911371100914112251130113161321341331");
		
		SecretSolution solution10 = new SecretSolution();
		solution10.addSecret(new SecretWall(7, 72, 18));
		solution10.addSecret(new SecretChest(7, 69, 19));
		solution10.addSecret(new SecretBat(19, 58, -5));
		solution10.addPathPoint(5, 69, -3);
		solution10.addPathPoint(-6, 65, -1);
		solution10.addPathPoint(-1, 62, 4);
		solution10.addPathPoint(3, 59, -2);
		solution10.addSecret(new SecretMisc(0, 51, 5));
		solution10.addPathPoint(14, 51, 43);
		solution10.addPathPoint(19, 47, 40);
		solution10.addPathPoint(7, 42, 40);
		solution10.addPathPoint(7, 42, 12);
		solution10.addSecret(new SecretChest(20, 42, 12));
		solutions.put("443529161372119711186266712317118311481340148713641641223611586180413113713241116711466116299115461105607112387347110012481198231401981125361511451788459115653172511281381571", solution10);
		redirects.put("2001201177411497119691181412411228111419918316018346281183140538111760114001184315151935011772155310017888101", "443529161372119711186266712317118311481340148713641641223611586180413113713241116711466116299115461105607112387347110012481198231401981125361511451788459115653172511281381571");
		
		SecretSolution solution11 = new SecretSolution();
		solution11.addSecret(new SecretChest(7, 70, 23));
		solution11.addSecret(new SecretWall(18, 62, 24));
		solution11.addPathPoint(21, 70, 24);
		solution11.addSecret(new SecretChest(15, 60, 24));
		solution11.addSecret(new SecretMisc(11, 61, 42));
		solution11.addPathPoint(12, 70, 48);
		solution11.addSecret(new SecretMisc(14, 61, 45));
		solution11.addSecret(new SecretChest(13, 94, -1));
		solutions.put("24121412416101107016101107011221580112212412141610110701662111389114451134711211219111572311153231", solution11);
		redirects.put("2311152231115223211522131219114861130616416115371214112212141241122115746161016929516101124951241771107011036016101104541", "24121412416101107016101107011221580112212412141610110701662111389114451134711211219111572311153231");
		
		SecretSolution solution12 = new SecretSolution();
		solution12.addSecret(new SecretMisc(5, 69, -26));
		solution12.addPathPoint(7, 69, 16);
		solution12.addPathPoint(10, 73, 9);
		solutions.put("312175602612141606134812141251121411501214112211913551159144612141234412141381612141340121415091410168913121479121415421214147512141", solution12);
		redirects.put("348115301348149160711741572150914741293147412921509129219081244114821244013481244112141121134811221348119134811912141509148217461", "312175602612141606134812141251121411501214112211913551159144612141234412141381612141340121415091410168913121479121415421214147512141");
		
		SecretSolution solution13 = new SecretSolution();
		solution13.addSecret(new SecretLever(20, 82, -9));
		solution13.addSecret(new SecretLever(16, 53, -9));
		solution13.addPathPoint(10, 63, -9);
		solution13.addPathPoint(16, 52, 13);
		solution13.addPathPoint(6, 57, 14);
		solution13.addPathPoint(6, 57, -10);
		solution13.addSecret(new SecretChest(6, 55, -37));
		solution13.addSecret(new SecretWall(16, 81, 6));
		solution13.addPathPoint(-1, 52, -1);
		solution13.addPathPoint(6, 57, -2);
		solution13.addPathPoint(6, 57, 14);
		solution13.addPathPoint(16, 52, 13);
		solution13.addPathPoint(10, 63, -9);
		solution13.addSecret(new SecretMisc(16, 81, 8));
		solution13.addSecret(new SecretChest(2, 107, -29));
		solution13.addPathPoint(9, 105, -24);
		solutions.put("19346013251193460130111255615100116673015811254480110095016148011547161393401509013500601114211416201134061183061601116701459013501711135112519655125282122030110181705118065188280188251", solution13);
		redirects.put("2940613353065512561417170041372910120857296018666828461239201461111149201976512875517858111828211246101444711196841331581689901293501376701658357713767014377189240123911255611050112826121416413013121", "19346013251193460130111255615100116673015811254480110095016148011547161393401509013500601114211416201134061183061601116701459013501711135112519655125282122030110181705118065188280188251");
		
	} //tpd
	
	private static void registerT() {
		
		SecretSolution solution0 = new SecretSolution();
		solution0.addSecret(new SecretChest(2, 60, 29));
		solution0.addPathPoint(-6, 69, 29);
		solution0.addSecret(new SecretWall(2, 70, 48));
		solution0.addSecret(new SecretMisc(-1, 69, 47));
		solution0.addSecret(new SecretLever(7, 70, 13));
		solution0.addSecret(new SecretChest(7, 69, -28));
		solution0.addSecret(new SecretChest(19, 92, 43));
		solution0.addPathPoint(13, 86, 1);
		solution0.addPathPoint(16, 84, 43);
		solution0.addSecret(new SecretBat(19, 94, 43));
		solution0.addSecret(new SecretChest(7, 82, 27));
		solutions.put("2181141217201315111912211200112961157411491112011191315121713151217131512351217126511102113781110211005112951129611199111031110211201161019051", solution0);
		redirects.put("139015831614142115511753131923821319121815081218134413151441131513821125110951645160925151609158713078125651307823660120751", "2181141217201315111912211200112961157411491112011191315121713151217131512351217126511102113781110211005112951129611199111031110211201161019051");
		redirects.put("4831515196513211135015151129711794190411738157915781484148213841484157826761578111971904170387451868651012143197112043081310041119811012613111503377511681911123805051", "2181141217201315111912211200112961157411491112011191315121713151217131512351217126511102113781110211005112951129611199111031110211201161019051");
		
		SecretSolution solution1 = new SecretSolution();
		solution1.addSecret(new SecretChest(19, 63, -16));
		solution1.addPathPoint(20, 63, -22);
		solution1.addSecret(new SecretWall(12, 51, -16));
		solution1.addSecret(new SecretChest(7, 50, -16));
		solution1.addSecret(new SecretChest(10, 49, 49));
		solution1.addPathPoint(13, 51, 43);
		solution1.addSecret(new SecretWall(4, 49, 42));
		solution1.addSecret(new SecretMisc(-3, 49, 41));
		solution1.addSecret(new SecretMisc(0, 50, 40));
		solution1.addSecret(new SecretChest(6, 63, 44));
		solution1.addPathPoint(-3, 58, 51);
		solution1.addPathPoint(12, 60, 36);
		solution1.addPathPoint(12, 64, 45);
		solutions.put("981019918121513731337190616921342119811390011475111901447511965149519935931238208", solution1);
		redirects.put("53513654153512898153512891136215361206121951732143912061856513170111035133471442013170149512041112511001237149511261991014951019811261", "981019918121513731337190616921342119811390011475111901447511965149519935931238208");
		redirects.put("0623711391513371393111621198199152812222118218251178147319851187511411336123711961115111971495126964199172271", "981019918121513731337190616921342119811390011475111901447511965149519935931238208");
		
		SecretSolution solution2 = new SecretSolution();
		solution2.addSecret(new SecretChest(43, 60, 1));
		solution2.addPathPoint(35, 69, 3);
		solution2.addPathPoint(34, 69, 16);
		solution2.addPathPoint(43, 68, 13);
		solution2.addSecret(new SecretBat(22, 70, -5));
		solution2.addPathPoint(16, 70, -2);
		solution2.addSecret(new SecretChest(-15, 60, 21));
		solution2.addPathPoint(-15, 70, 5);
		solution2.addSecret(new SecretChest(-36, 77, 6));
		solution2.addPathPoint(-30, 77, 3);
		solutions.put("125113011251636125151301349163611251251477811251643112513581251357612517056125113001125163011251223124122312512231125113011231", solution2);
		redirects.put("371228113713115281522136411109837554148601488171412953181119251520179581423116021423118061226185413261606122715721134401314841165051278113712141", "125113011251636125151301349163611251251477811251643112513581251357612517056125113001125163011251223124122312512231125113011231");
		redirects.put("24171112418111251613125171311561709135717711956431360671120019551111017232122116261130133301123128481209011367161519713631361121811371", "125113011251636125151301349163611251251477811251643112513581251357612517056125113001125163011251223124122312512231125113011231");
		
		SecretSolution solution3 = new SecretSolution();
		solution3.addSecret(new SecretLever(-3, 69, 14));
		solution3.addSecret(new SecretChest(-3, 70, 17));
		solution3.addSecret(new SecretMisc(14, 86, 19));
		solution3.addPathPoint(13, 85, 7);
		solution3.addSecret(new SecretChest(18, 90, -1));
		solution3.addPathPoint(21, 89, 3);
		solution3.addSecret(new SecretMisc(7, 83, 39));
		solutions.put("755826156676118128611121181762118120911121973111211814001591177812091112118111221813801538118127811821259701755826138242395617558261", solution3);
		redirects.put("499222350248192895218411211811121181112418111211848952481924992223502503411511", "755826156676118128611121181762118120911121973111211814001591177812091112118111221813801538118127811821259701755826138242395617558261");
		redirects.put("-20597459382151289521121181211211811121181112189521512-205974593821512", "755826156676118128611121181762118120911121973111211814001591177812091112118111221813801538118127811821259701755826138242395617558261");
		
		SecretSolution solution4 = new SecretSolution();
		solution4.addSecret(new SecretLever(-9, 69, 18));
		solution4.addSecret(new SecretChest(19, 69, 3));
		solution4.addSecret(new SecretBat(19, 89, 4));
		solution4.addPathPoint(14, 69, -3);
		solution4.addSecret(new SecretMisc(-13, 87, 5));
		solution4.addPathPoint(-17, 81, -4);
		solution4.addPathPoint(-15, 81, 6);
		solution4.addSecret(new SecretMisc(-12, 87, 6));
		solution4.addSecret(new SecretMisc(-38, 69, -5));
		solution4.addPathPoint(-28, 73, -5);
		solution4.addSecret(new SecretMisc(-33, 69, 0));
		solutions.put("97142545751971697118511261161521126118643692451126115781116312625131201157416271826511480126251298126251103173315001985121451262515051", solution4);
		redirects.put("379142513831971579182917751425139114251264012055315311583480422198517831203166615311666171911991971123051126114641126111675112611331511261133151", "97142545751971697118511261161521126118643692451126115781116312625131201157416271826511480126251298126251103173315001985121451262515051");
		redirects.put("86133141311152111930891914251131114251141314251111119714251971837197115619715091972425297142539714254971", "97142545751971697118511261161521126118643692451126115781116312625131201157416271826511480126251298126251103173315001985121451262515051");
		
		SecretSolution solution5 = new SecretSolution();
		solution5.addSecret(new SecretWall(44, 70, 15));
		solution5.addSecret(new SecretMisc(51, 76, 14));
		solution5.addSecret(new SecretBat(6, 58, 19));
		solution5.addSecret(new SecretWall(48, 92, 12));
		solution5.addPathPoint(26, 69, 7);
		solution5.addPathPoint(28, 81, 16);
		solution5.addPathPoint(49, 81, 16);
		solution5.addPathPoint(48, 81, 5);
		solution5.addSecret(new SecretChest(37, 92, 18));
		solution5.addSecret(new SecretWall(-20, 83, 16));
		solution5.addPathPoint(48, 81, 5);
		solution5.addPathPoint(49, 81, 16);
		solution5.addPathPoint(-5, 88, 16);
		solution5.addSecret(new SecretChest(-36, 89, 16));
		solutions.put("1081398124215113701661121009114861293511179517475141411214061245561302078124556230207812455612140611495174752149517475113507211163115111572513801108111311081", solution5);
		redirects.put("1038380161659601235772618888541161115424918654621533913584951443091801255514875123948331493140843251364511627283171951127430511183191105535311149841117811684461469061221801107479961221801261611082523511275461", "1081398124215113701661121009114861293511179517475141411214061245561302078124556230207812455612140611495174752149517475113507211163115111572513801108111311081");
		redirects.put("4243112811584771155147102121745287419441254012458141970133944418151366099721201363718321100123206861961241211631432111916991195011824371143793401180297811520512437791106120606317516051", "1081398124215113701661121009114861293511179517475141411214061245561302078124556230207812455612140611495174752149517475113507211163115111572513801108111311081");
		
		SecretSolution solution6 = new SecretSolution();
		solution6.addSecret(new SecretWall(0, 72, -33));
		solution6.addSecret(new SecretMisc(7, 86, -20));
		solution6.addPathPoint(-5, 72, -37);
		solution6.addPathPoint(-5, 86, -21);
		solution6.addSecret(new SecretMisc(2, 86, -36));
		solution6.addSecret(new SecretBat(5, 52, -25));
		solution6.addPathPoint(-5, 86, -21);
		solution6.addPathPoint(-5, 72, -37);
		solution6.addPathPoint(7, 69, -13);
		solution6.addPathPoint(5, 53, -7);
		solution6.addSecret(new SecretMisc(7, 57, 50));
		solution6.addPathPoint(11, 58, 33);
		solution6.addSecret(new SecretChest(6, 87, -11));
		solutions.put("87947481711411561759368351139698148524214711469881-8983060601115966621539151301120976481718423488171121278717110857456171977185351148771176026535613699015228070211369911576124381381251420941140427120085314087138124129611353241", solution6);
		redirects.put("231179152617816261771387017612341761231113012311761134176123114651329176165513511211272843901204137466911131699127118101898165740681341", "87947481711411561759368351139698148524214711469881-8983060601115966621539151301120976481718423488171121278717110857456171977185351148771176026535613699015228070211369911576124381381251420941140427120085314087138124129611353241");
		redirects.put("9031-165696425011195117761215131412111541146420114612011461105214614141212148019029418190294281902925119029350190292931902941911471270143813591", "87947481711411561759368351139698148524214711469881-8983060601115966621539151301120976481718423488171121278717110857456171977185351148771176026535613699015228070211369911576124381381251420941140427120085314087138124129611353241");
		
	} //tpt
	
	private static void registerQ() {
		
		SecretSolution solution0 = new SecretSolution();
		solution0.addSecret(new SecretWall(25, 75, -2));
		solution0.addSecret(new SecretChest(26, 69, -5));
		solution0.addSecret(new SecretChest(-31, 85, -2));
		solution0.addPathPoint(-28, 79, -7);
		solution0.addSecret(new SecretLever(-38, 76, 12));
		solution0.addSecret(new SecretChest(0, 69, -3));
		solution0.addPathPoint(-20, 70, -6);
		solution0.addPathPoint(-5, 70, -3);
		solutions.put("7613091161271125301173175215175215217311511731151173175215175211311731408121112040121118014671801", solution0);
		redirects.put("309177361271117638551467176918612721191570175157011512111151174115157017515701791100118411781109712830116784897851967163011781489951380143551-17887587611967163015401505351380143551", "7613091161271125301173175215175215217311511731151173175215175211311731408121112040121118014671801");
		redirects.put("36911144804512111114117318701112117311527511517511527511542711151309115128501173160827861211112731309157114071", "7613091161271125301173175215175215217311511731151173175215175211311731408121112040121118014671801");
		redirects.put("3819513091176385512711302011131369115375715375115031151151911093110971303951-80279239813919176597811611883941", "7613091161271125301173175215175215217311511731151173175215175211311731408121112040121118014671801");
		
		SecretSolution solution1 = new SecretSolution();
		solution1.addSecret(new SecretWall(-63, 88, -5));
		solution1.addSecret(new SecretChest(-57, 88, -6));
		solution1.addSecret(new SecretMisc(-41, 79, 19));
		solution1.addSecret(new SecretBat(26, 58, -2));
		solution1.addSecret(new SecretChest(36, 90, 16));
		solution1.addPathPoint(47, 90, 10);
		solution1.addSecret(new SecretChest(33, 89, 16));
		solution1.addPathPoint(26, 90, 12);
		solution1.addSecret(new SecretChest(48, 63, 19));
		solution1.addPathPoint(40, 70, 12);
		solution1.addSecret(new SecretMisc(-14, 84, -2));
		solution1.addPathPoint(7, 78, 2);
		solution1.addPathPoint(-7, 86, -6);
		solutions.put("320305181166683514029168471712540091161466451159272757114475450941520521263476641859241-8608494331540401711132921177275415511082159150780311348891117416551-10112757611157861955518475936114172955157760509413189890719742441102729331-5375279914028677571204868448194883591-960477429124620871", solution1);
		redirects.put("73611-58094569518081142090329411896114730845141727153690361145114253672521391185215319641173945351966163020155171861139471111610118457413119687131561130124712227421651110738786181815507938193411040919413341426042861", "320305181166683514029168471712540091161466451159272757114475450941520521263476641859241-8608494331540401711132921177275415511082159150780311348891117416551-10112757611157861955518475936114172955157760509413189890719742441102729331-5375279914028677571204868448194883591-960477429124620871");
		redirects.put("1486132414308811991106535117831391112819711206414151160912914128171293666161511991130911566166126796145581100631379114491331118901831116271407913575114521", "320305181166683514029168471712540091161466451159272757114475450941520521263476641859241-8608494331540401711132921177275415511082159150780311348891117416551-10112757611157861955518475936114172955157760509413189890719742441102729331-5375279914028677571204868448194883591-960477429124620871");
		redirects.put("34474911375134474915348310170901995116598426113511688813059572961497296272180595787611012415155151420484719130739455511674058639124010555167526913614551551-173342646414563291111175662813662125261209300047194897935195884811436584281674206211376502127349116192896132241551", "320305181166683514029168471712540091161466451159272757114475450941520521263476641859241-8608494331540401711132921177275415511082159150780311348891117416551-10112757611157861955518475936114172955157760509413189890719742441102729331-5375279914028677571204868448194883591-960477429124620871");
		
		SecretSolution solution2 = new SecretSolution();
		solution2.addSecret(new SecretBat(-4, 62, 30));
		solution2.addSecret(new SecretChest(7, 81, 81));
		solution2.addPathPoint(7, 86, 59);
		solution2.addSecret(new SecretChest(-5, 78, 42));
		solution2.addPathPoint(-3, 82, 45);
		solution2.addSecret(new SecretBat(18, 45, 48));
		solution2.addPathPoint(11, 57, 8);
		solution2.addPathPoint(5, 43, 6);
		solution2.addPathPoint(0, 44, 34);
		solution2.addSecret(new SecretWall(19, 45, 7));
		solution2.addPathPoint(-1, 44, 30);
		solution2.addPathPoint(8, 44, 1);
		solution2.addPathPoint(17, 44, 3);
		solution2.addSecret(new SecretChest(19, 44, 13));
		solution2.addSecret(new SecretMisc(20, 44, -32));
		solution2.addPathPoint(-1, 44, -7);
		solution2.addPathPoint(4, 44, -35);
		solution2.addPathPoint(14, 44, -35);
		solution2.addSecret(new SecretChest(15, 84, -6));
		solution2.addPathPoint(3, 44, -33);
		solution2.addPathPoint(5, 79, -25);
		solution2.addPathPoint(4, 81, -12);
		solution2.addSecret(new SecretChest(13, 82, 2));
		solution2.addPathPoint(2, 79, -24);
		solution2.addPathPoint(-3, 69, -26);
		solution2.addPathPoint(3, 61, 8);
		solution2.addPathPoint(17, 82, 6);
		solutions.put("34133115121183115201121641661431153011314572396153114461233114451233114461531143149114011311431383126917553123134164114811020081701", solution2);
		redirects.put("141611418113111112113161131214910113101121243129401181161111157681270116111144123127571151138212819492611451", "34133115121183115201121641661431153011314572396153114461233114451233114461531143149114011311431383126917553123134164114811020081701");
		redirects.put("4418699051114711404291244132415941121359857148142661240188124015214815214815214415111431156111881461146144114214811461361143159761", "34133115121183115201121641661431153011314572396153114461233114451233114461531143149114011311431383126917553123134164114811020081701");
		redirects.put("787130101-146672575811997427582120101998015080110251263012530110215061220118015144114831491801624301151418011105113071200135001505141011117251151925519911", "34133115121183115201121641661431153011314572396153114461233114451233114461531143149114011311431383126917553123134164114811020081701");
		
		SecretSolution solution3 = new SecretSolution();
		solution3.addSecret(new SecretChest(20, 92, 105));
		solution3.addPathPoint(-3, 69, 104);
		solution3.addPathPoint(-4, 77, 114);
		solution3.addSecret(new SecretWall(15, 77, 0));
		solution3.addSecret(new SecretMisc(17, 76, -5));
		solution3.addSecret(new SecretBat(19, 78, 0));
		solution3.addSecret(new SecretChest(-1, 65, -1));
		solution3.addPathPoint(-4, 72, -7);
		solution3.addSecret(new SecretChest(1, 91, 12));
		solution3.addPathPoint(-2, 76, 14);
		solution3.addPathPoint(18, 83, -1);
		solutions.put("90011681426119741744127231158566115894211636521345271156136361422012231373011231422011221830138321950227198142819461474214501122975111001112016201615160611221", solution3);
		redirects.put("57112312611231261581150112312461121124612513111271123224273112311107112311321271361271132120813611231132167715337111241", "90011681426119741744127231158566115894211636521345271156136361422012231373011231422011221830138321950227198142819461474214501122975111001112016201615160611221");
		redirects.put("221219029229311901123122111587113921158611628112111231121121312311151213212311211129511504114881139211231213112311211", "90011681426119741744127231158566115894211636521345271156136361422012231373011231422011221830138321950227198142819461474214501122975111001112016201615160611221");
		redirects.put("2512131140711254114891172511231213112312131251115112312411231121116271143211602114471221112411902922190193122121684117251", "90011681426119741744127231158566115894211636521345271156136361422012231373011231422011221830138321950227198142819461474214501122975111001112016201615160611221");
		
		SecretSolution solution4 = new SecretSolution();
		solution4.addSecret(new SecretWall(18, 72, 23));
		solution4.addSecret(new SecretLever(21, 70, 23));
		solution4.addSecret(new SecretChest(7, 95, 23));
		solution4.addSecret(new SecretBat(7, 93, 32));
		solution4.addSecret(new SecretChest(-4, 89, 7));
		solution4.addSecret(new SecretWall(0, 70, -35));
		solution4.addSecret(new SecretChest(-4, 69, -38));
		solutions.put("1579112031142711481117275117175118117311161173111611731116117311163752171531481171131711203112281138311093111911", solution4);
		redirects.put("3801661315195222121419511032291480137313151316179121819512751380117716617913801120138015761791389148613731389175613571", "1579112031142711481117275117175118117311161173111611731116117311163752171531481171131711203112281138311093111911");
		redirects.put("122811383113171120314811725317521162173111611731116117311161173111617511817511731427114811157911203111011231", "1579112031142711481117275117175118117311161173111611731116117311163752171531481171131711203112281138311093111911");
		redirects.put("67413162185157617911201380179138011771661275138012181951316179137313151103229148012141952221315195138016619911881", "1579112031142711481117275117175118117311161173111611731116117311163752171531481171131711203112281138311093111911");
		
		SecretSolution solution5 = new SecretSolution();
		solution5.addSecret(new SecretWall(-2, 55, 38));
		solution5.addSecret(new SecretChest(-3, 54, 47));
		solution5.addSecret(new SecretWall(0, 56, 47));
		solution5.addSecret(new SecretBat(7, 58, 50));
		solution5.addSecret(new SecretChest(18, 62, 45));
		solution5.addSecret(new SecretChest(7, 45, 35));
		solution5.addPathPoint(1, 54, 27);
		solution5.addPathPoint(6, 40, 25);
		solution5.addPathPoint(-3, 45, 39);
		solution5.addPathPoint(0, 45, 49);
		solution5.addSecret(new SecretWall(8, 48, -66));
		solution5.addPathPoint(7, 42, -49);
		solution5.addPathPoint(16, 48, -60);
		solution5.addSecret(new SecretChest(-5, 42, -65));
		solution5.addSecret(new SecretLever(7, 94, -20));
		solution5.addSecret(new SecretChest(18, 55, -9));
		solution5.addSecret(new SecretWall(-1, 43, -39));
		solution5.addSecret(new SecretMisc(-2, 32, -23));
		solutions.put("10921130611308152216251525170801619901011981014951012951011182161606161137818911117711083141472198112511771130071530516071", solution5);
		redirects.put("11951807711687438729188871306711201196716711321115791217914531146571159715951129713697125691425513943139851240115389118131481911284712531241139251167471451170271", "10921130611308152216251525170801619901011981014951012951011182161606161137818911117711083141472198112511771130071530516071");
		redirects.put("2444511080654971220911371112641165531447543109185696144713451134411512173381744145661346161212201115127251605131151261123912621321135414201228171815883683181419101", "10921130611308152216251525170801619901011981014951012951011182161606161137818911117711083141472198112511771130071530516071");
		redirects.put("2983131871149171241912995166711559513805158691292911263712106618871307311969166371300071369711969142731382311927126095149399123665166451174227110719117464711333123689125151161471", "10921130611308152216251525170801619901011981014951012951011182161606161137818911117711083141472198112511771130071530516071");
		
	} //tpq
	
	private static void registerB() {
		
		SecretSolution solution0 = new SecretSolution();
		solution0.addSecret(new SecretChest(-8, 64, -11));
		solution0.addPathPoint(8, 70, -15);
		solution0.addPathPoint(4, 62, -13);
		solution0.addPathPoint(-2, 62, -16);
		solution0.addSecret(new SecretMisc(16, 49, -30));
		solution0.addPathPoint(5, 62, -17);
		solution0.addPathPoint(8, 54, -31);
		solution0.addPathPoint(18, 58, -37);
		solution0.addSecret(new SecretMisc(19, 49, -30));
		solution0.addSecret(new SecretBat(-28, 51, -28));
		solution0.addPathPoint(18, 57, -36);
		solution0.addPathPoint(5, 61, -25);
		solution0.addPathPoint(-4, 63, -28);
		solution0.addPathPoint(-26, 49, -10);
		solution0.addSecret(new SecretChest(-8, 72, -6));
		solution0.addPathPoint(-26, 49, -10);
		solution0.addPathPoint(-27, 69, -17);
		solution0.addPathPoint(-29, 74, -9);
		solution0.addPathPoint(-18, 72, -9);
		solution0.addSecret(new SecretWall(-34, 68, -34));
		solution0.addPathPoint(-19, 72, -9);
		solution0.addPathPoint(-23, 69, -18);
		solution0.addPathPoint(-34, 69, -25);
		solution0.addSecret(new SecretChest(-34, 66, -34));
		solution0.addSecret(new SecretMisc(-5, 70, -12));
		solution0.addPathPoint(-24, 69, -20);
		solution0.addPathPoint(-32, 69, 8);
		solution0.addPathPoint(-19, 61, 15);
		solution0.addPathPoint(-9, 61, 15);
		solution0.addPathPoint(-7, 71, -3);
		solution0.addSecret(new SecretMisc(-3, 70, -12));
		solution0.addSecret(new SecretLever(-17, 71, 5));
		solution0.addSecret(new SecretChest(14, 49, 20));
		solution0.addPathPoint(0, 50, 15);
		solutions.put("55019061910110886819280183313148184813610174497785418791161141132241214194715511182219111822158613518321257181819501164413151322195291788613841158101611", solution0);
		redirects.put("7271129218121110018121206813931141612361133858121218413146411109411365155871252182873921386155024138714752412042018831405249511110555110393611078143391191411979112701", "55019061910110886819280183313148184813610174497785418791161141132241214194715511182219111822158613518321257181819501164413151322195291788613841158101611");
		redirects.put("8441997119180165511945911957812582081937217745985117888134481999212461139512471139513931139518171300318171302111131139511451139516287116735613145762113828111742412621", "55019061910110886819280183313148184813610174497785418791161141132241214194715511182219111822158613518321257181819501164413151322195291788613841158101611");
		redirects.put("78911791159713303118017021527291-13301091301208214111245491102144068821136116500828314113243314131141311413111441311277123113701121162312191432011211", "55019061910110886819280183313148184813610174497785418791161141132241214194715511182219111822158613518321257181819501164413151322195291788613841158101611");
		
		SecretSolution solution1 = new SecretSolution();
		solution1.addSecret(new SecretWall(-30, 81, -31));
		solution1.addPathPoint(-9, 71, -34);
		solution1.addPathPoint(-32, 69, -32);
		solution1.addPathPoint(-32, 71, -37);
		solution1.addPathPoint(-37, 73, -37);
		solution1.addPathPoint(-37, 80, -26);
		solution1.addSecret(new SecretLever(-30, 79, -31));
		solution1.addSecret(new SecretChest(-19, 80, -28));
		solution1.addSecret(new SecretChest(13, 108, -23));
		solution1.addPathPoint(-22, 85, -29);
		solution1.addPathPoint(-2, 100, -33);
		solution1.addPathPoint(13, 102, -33);
		solution1.addSecret(new SecretWall(9, 111, -19));
		solution1.addSecret(new SecretWall(13, 111, -12));
		solution1.addSecret(new SecretChest(13, 109, -10));
		solution1.addSecret(new SecretWall(8, 82, -35));
		solution1.addPathPoint(8, 102, -33);
		solution1.addPathPoint(-1, 88, -36);
		solution1.addPathPoint(2, 83, -25);
		solution1.addPathPoint(8, 81, -25);
		solution1.addSecret(new SecretLever(8, 80, -37));
		solution1.addSecret(new SecretChest(13, 82, -19));
		solution1.addPathPoint(2, 83, -25);
		solution1.addPathPoint(-1, 88, -36);
		solution1.addPathPoint(-6, 90, -19);
		solution1.addPathPoint(3, 68, -13);
		solution1.addPathPoint(19, 67, -16);
		solution1.addPathPoint(18, 81, -17);
		solution1.addSecret(new SecretChest(-9, 99, 18));
		solution1.addPathPoint(18, 67, -16);
		solution1.addPathPoint(-10, 98, 1);
		solution1.addSecret(new SecretLever(-12, 79, 19));
		solution1.addPathPoint(-10, 98, 1);
		solution1.addPathPoint(-16, 89, 8);
		solution1.addPathPoint(-19, 89, 19);
		solution1.addPathPoint(-12, 78, 16);
		solution1.addSecret(new SecretChest(-34, 78, 18));
		solution1.addSecret(new SecretWall(16, 90, 17));
		solution1.addPathPoint(-21, 79, 10);
		solution1.addPathPoint(13, 89, 10);
		solution1.addSecret(new SecretMisc(20, 89, 17));
		solution1.addSecret(new SecretMisc(-30, 48, -32));
		solution1.addPathPoint(13, 89, 10);
		solution1.addPathPoint(-30, 49, -14);
		solution1.addPathPoint(-31, 47, -26);
		solution1.addSecret(new SecretChest(-38, 43, 10));
		solution1.addPathPoint(-28, 42, -6);
		solution1.addPathPoint(-34, 43, 2);
		solution1.addPathPoint(-38, 44, 6);
		solution1.addSecret(new SecretMisc(17, 54, 15));
		solution1.addPathPoint(-23, 42, -7);
		solution1.addPathPoint(10, 52, -5);
		solution1.addPathPoint(16, 55, 5);
		solutions.put("01367313088791254713836713441816986110114565110114577111114565114421145651144211480311446114699118351230111734918391217115645170113400312621133737131618102041", solution1);
		redirects.put("531188461221591271812052125227161127874331255811216568011056551240475137171154011450312511449111911456611911476612921803136601100013203811561248120011811561181", "01367313088791254713836713441816986110114565110114577111114565114421145651144211480311446114699118351230111734918391217115645170113400312621133737131618102041");
		redirects.put("152212851217123219114921217151411304071-142121417911324941-19631054731146831-14269788861150241119411462011072904811755118226117441472189711510031796155296114041624140797172116157351202015041981", "01367313088791254713836713441816986110114565110114577111114565114421145651144211480311446114699118351230111734918391217115645170113400312621133737131618102041");
		redirects.put("1241518116814881451110771152304918541-140391968212661-19631053791931-1968870180112790110021750190317501717101861741114712181504915661149413150050161147961168957113015037411891780761", "01367313088791254713836713441816986110114565110114577111114565114421145651144211480311446114699118351230111734918391217115645170113400312621133737131618102041");
		
		SecretSolution solution2 = new SecretSolution();
		solution2.addSecret(new SecretBat(47, 72, 15));
		solution2.addSecret(new SecretChest(23, 78, 12));
		solution2.addPathPoint(42, 71, 15);
		solution2.addSecret(new SecretMisc(-2, 75, -13));
		solution2.addPathPoint(1, 78, 3);
		solution2.addSecret(new SecretLever(-6, 90, -7));
		solution2.addSecret(new SecretChest(43, 82, -9));
		solution2.addPathPoint(47, 78, -23);
		solution2.addSecret(new SecretChest(18, 57, -25));
		solution2.addPathPoint(4, 66, -31);
		solution2.addSecret(new SecretMisc(16, 78, -36));
		solutions.put("1491801168180117561191199311919198918512118515131214114711842114711614611471227597581147196196464151311634512111306899119111323794614920611179431161381892361", solution2);
		redirects.put("1611629911811175234411617015106119121185121185116201116116211445633117469169101189271101117469193052081106221609117551260442131134514071271407124127913589116211", "1491801168180117561191199311919198918512118515131214114711842114711614611471227597581147196196464151311634512111306899119111323794614920611179431161381892361");
		redirects.put("51215231610144916871946118438161212040180519716091145411191193911191117111918512211851511116141411914141191744116184111818041", "1491801168180117561191199311919198918512118515131214114711842114711614611471227597581147196196464151311634512111306899119111323794614920611179431161381892361");
		redirects.put("6113671611562159110011103201465715728201182416011174817731684416551183512845149125111115179912701894129751132119148451181366118120764601861161", "1491801168180117561191199311919198918512118515131214114711842114711614611471227597581147196196464151311634512111306899119111323794614920611179431161381892361");
		
		SecretSolution solution3 = new SecretSolution();
		solution3.addSecret(new SecretWall(-9, 70, 17));
		solution3.addSecret(new SecretChest(-9, 70, 20));
		solution3.addSecret(new SecretWall(-21, 68, -34));
		solution3.addSecret(new SecretChest(-26, 62, -34));
		solution3.addSecret(new SecretMisc(-20, 93, 13));
		solution3.addPathPoint(-9, 82, 8);
		solution3.addSecret(new SecretChest(-12, 83, -29));
		solution3.addPathPoint(-25, 82, -9);
		solution3.addPathPoint(-30, 82, -26);
		solution3.addSecret(new SecretLever(-4, 84, -31));
		solution3.addPathPoint(6, 81, -9);
		solution3.addPathPoint(15, 82, -33);
		solution3.addSecret(new SecretChest(-9, 70, -31));
		solutions.put("37114713711481371246137114913712311171115185820427811511732911311407114913531231213195140911331409114911096136019981159170611591", solution3);
		redirects.put("132511511128413510111141169116381241121961241127431231116212411599912411575214301241110912312112312111151211231211221214", "37114713711481371246137114913712311171115185820427811511732911311407114913531231213195140911331409114911096136019981159170611591");
		redirects.put("40911114143811331199019961151131311251132211511322111511301611511624411151858204169124111171241137124113711151371241137114913711491371", "37114713711481371246137114913712311171115185820427811511732911311407114913531231213195140911331409114911096136019981159170611591");
		redirects.put("24112112411211115121124112111491571149114012315752035012411599211511317115113171251122118214091892131717941997131911004122119001", "37114713711481371246137114913712311171115185820427811511732911311407114913531231213195140911331409114911096136019981159170611591");
		
		SecretSolution solution4 = new SecretSolution();
		solution4.addSecret(new SecretChest(-15, 67, 18));
		solution4.addPathPoint(-5, 71, 6);
		solution4.addSecret(new SecretLever(-9, 71, -9));
		solution4.addSecret(new SecretChest(11, 97, -33));
		solution4.addPathPoint(-5, 69, -25);
		solution4.addPathPoint(-10, 76, -36);
		solution4.addPathPoint(-13, 85, -23);
		solution4.addPathPoint(0, 96, -34);
		solution4.addSecret(new SecretChest(8, 108, 1));
		solution4.addPathPoint(-3, 96, -19);
		solution4.addPathPoint(2, 108, -6);
		solution4.addSecret(new SecretChest(8, 108, -13));
		solution4.addSecret(new SecretChest(-37, 87, 1));
		solution4.addPathPoint(-4, 96, 4);
		solution4.addPathPoint(-30, 95, 12);
		solution4.addPathPoint(-33, 88, 7);
		solution4.addSecret(new SecretBat(-32, 70, -2));
		solution4.addPathPoint(-31, 87, -3);
		solution4.addSecret(new SecretMisc(-31, 69, -17));
		solution4.addPathPoint(-31, 73, -10);
		solutions.put("26797022180913324110981114151241145114781243122011171245141113431313131519041246180813431129313331904155251100411782180711197110981111311199111001", solution4);
		redirects.put("1195110011110111195180911098170911199111981117180512964190417755141111912701120113517081117121811201743355081114124713359011098123308501809120813441", "26797022180913324110981114151241145114781243122011171245141113431313131519041246180813431129313331904155251100411782180711197110981111311199111001");
		redirects.put("57081109811100180411030110011341190413411129311201808111719041120111371215142912151911313111711098111711196111413558912713324180916206131513193151", "26797022180913324110981114151241145114781243122011171245141113431313131519041246180813431129313331904155251100411782180711197110981111311199111001");
		redirects.put("8091979811098133591102211141149411201197775119154531138153551270121701414127580190412118051280011195139955170912151809167643111011426111951325111296621", "26797022180913324110981114151241145114781243122011171245141113431313131519041246180813431129313331904155251100411782180711197110981111311199111001");
		
		SecretSolution solution5 = new SecretSolution();
		solution5.addSecret(new SecretChest(14, 70, -31));
		solution5.addPathPoint(15, 73, -27);
		solution5.addSecret(new SecretWall(-17, 91, 4));
		solution5.addPathPoint(-4, 69, -20);
		solution5.addPathPoint(-10, 90, 0);
		solution5.addSecret(new SecretChest(-22, 88, -12));
		solution5.addSecret(new SecretMisc(-16, 86, -5));
		solution5.addPathPoint(-18, 90, 4);
		solution5.addPathPoint(-10, 90, 0);
		solution5.addSecret(new SecretChest(-9, 88, -18));
		solutions.put("27122189811518991181705118170611514271181271181271151160111512326171171221171151171151171151393137120513911111", solution5);
		redirects.put("85812717851119112161517121518091313190917851717178715161310161512151123121311261652180517061612131212201312121713141217170114201", "27122189811518991181705118170611514271181271181271151160111512326171171221171151171151171151393137120513911111");
		redirects.put("181328411811469891181271151271151271181301171241171241531221156912113741207126971201261251311170612212713161271", "27122189811518991181705118170611514271181271181271151160111512326171171221171151171151171151393137120513911111");
		redirects.put("2712212414312413112112181215155371218127627612314381160112513811221380132137913213047132131934129110931124127112412711271", "27122189811518991181705118170611514271181271181271151160111512326171171221171151171151171151393137120513911111");
		
		SecretSolution solution6 = new SecretSolution();
		solution6.addSecret(new SecretWall(38, 68, 23));
		solution6.addSecret(new SecretChest(1, 55, 23));
		solution6.addSecret(new SecretMisc(35, 80, 39));
		solution6.addPathPoint(38, 68, 23);
		solution6.addSecret(new SecretChest(52, 81, 42));
		solution6.addSecret(new SecretLever(-4, 70, 28));
		solution6.addPathPoint(38, 82, 29);
		solution6.addPathPoint(5, 70, 26);
		solution6.addSecret(new SecretChest(23, 73, 5));
		solution6.addPathPoint(39, 69, 16);
		solution6.addPathPoint(25, 73, 10);
		solution6.addSecret(new SecretWall(-2, 70, 44));
		solution6.addPathPoint(39, 70, 16);
		solution6.addPathPoint(8, 70, 27);
		solution6.addSecret(new SecretChest(-3, 69, 52));
		solution6.addSecret(new SecretWall(15, 81, 49));
		solution6.addPathPoint(-2, 69, 40);
		solution6.addPathPoint(22, 78, 49);
		solution6.addSecret(new SecretLever(12, 81, 49));
		solution6.addSecret(new SecretChest(-2, 81, -6));
		solution6.addPathPoint(7, 70, 40);
		solution6.addPathPoint(13, 70, 12);
		solution6.addPathPoint(-2, 70, 9);
		solution6.addPathPoint(-2, 80, 23);
		solutions.put("435112101235154116514767851339123513291141918531411126014051171127911324123041143491156017229122891647141615110481284853016831830513251106551325152812064921", solution6);
		redirects.put("4231186117581253051620142681351318171326165251211611308118213807881182125480138110101371119051182123516721168831138138961373937811035153313331194331572110541", "435112101235154116514767851339123513291141918531411126014051171127911324123041143491156017229122891647141615110481284853016831830513251106551325152812064921");
		redirects.put("52616291332134111526162413311366113711212113912331-17356409041474179313301784113411660127811801134113313231111513331284765515281382601598124117791", "435112101235154116514767851339123513291141918531411126014051171127911324123041143491156017229122891647141615110481284853016831830513251106551325152812064921");
		redirects.put("144611441303014715701881534097112615271185166201453112245167511575167013711865351801185115619051315511575111201329126201127690333414191247111251338113872412411", "435112101235154116514767851339123513291141918531411126014051171127911324123041143491156017229122891647141615110481284853016831830513251106551325152812064921");
		
		SecretSolution solution7 = new SecretSolution();
		solution7.addSecret(new SecretWall(13, 70, 14));
		solution7.addSecret(new SecretChest(3, 60, -1));
		solution7.addSecret(new SecretMisc(-26, 59, 15));
		solution7.addPathPoint(-25, 69, -3);
		solution7.addPathPoint(-31, 60, 11);
		solution7.addSecret(new SecretBat(-17, 75, 5));
		solution7.addSecret(new SecretBat(-30, 90, -4));
		solution7.addPathPoint(-32, 68, 1);
		solution7.addSecret(new SecretWall(-9, 82, 1));
		solution7.addSecret(new SecretChest(-6, 82, -5));
		solution7.addSecret(new SecretMisc(1, 90, 36));
		solution7.addPathPoint(-9, 66, 23);
		solution7.addPathPoint(19, 74, 23);
		solution7.addSecret(new SecretMisc(-10, 93, 39));
		solution7.addPathPoint(11, 89, 42);
		solution7.addPathPoint(1, 83, 50);
		solution7.addPathPoint(-13, 81, 48);
		solution7.addSecret(new SecretWall(-23, 60, 34));
		solution7.addSecret(new SecretChest(-16, 59, 28));
		solutions.put("205711314110257114914864101312184270137315751122511307112251115159011171122516011122511585139016220316661408119411511292175519614201361911361511", solution7);
		redirects.put("1441165114413346114413579151144187551181614147701175118601351372118011912001748114911234162203166612609012321463123212609513617601361260601361", "205711314110257114914864101312184270137315751122511307112251115159011171122516011122511585139016220316661408119411511292175519614201361911361511");
		redirects.put("46571621383175249501191213900451751179892601801583881-1467911374114750114881606801312213759913511030041499515920186161911103011426753116115201435158375123111100986001157014751416195142801", "205711314110257114914864101312184270137315751122511307112251115159011171122516011122511585139016220316661408119411511292175519614201361911361511");
		redirects.put("36132281361316012321132123211578016220316661656115851636131913280170911401308013160154811792130321470187455156120120013951291011648163018240113014151", "205711314110257114914864101312184270137315751122511307112251115159011171122516011122511585139016220316661408119411511292175519614201361911361511");
		
		SecretSolution solution8 = new SecretSolution();
		solution8.addSecret(new SecretBat(49, 72, -36));
		solution8.addSecret(new SecretLever(4, 77, 6));
		solution8.addSecret(new SecretMisc(23, 102, 13));
		solution8.addPathPoint(15, 73, -9);
		solution8.addPathPoint(43, 88, -9);
		solution8.addPathPoint(36, 91, 10);
		solution8.addPathPoint(-1, 91, 10);
		solution8.addPathPoint(-2, 93, 19);
		solution8.addSecret(new SecretLever(45, 98, -16));
		solution8.addPathPoint(-2, 93, 19);
		solution8.addPathPoint(9, 98, -24);
		solution8.addSecret(new SecretLever(42, 131, -33));
		solution8.addPathPoint(9, 98, -24);
		solution8.addPathPoint(9, 95, -9);
		solution8.addPathPoint(40, 110, -9);
		solution8.addSecret(new SecretBat(3, 111, -32));
		solution8.addPathPoint(39, 113, 8);
		solution8.addPathPoint(8, 110, 7);
		solution8.addPathPoint(10, 111, -26);
		solution8.addSecret(new SecretChest(18, 142, 11));
		solution8.addPathPoint(0, 111, -20);
		solution8.addPathPoint(-3, 117, -36);
		solution8.addPathPoint(23, 117, -28);
		solution8.addPathPoint(23, 141, 0);
		solution8.addSecret(new SecretChest(23, 142, 16));
		solution8.addSecret(new SecretChest(28, 142, 11));
		solution8.addSecret(new SecretLever(1, 142, 11));
		solutions.put("1481165712211677122146012214201221163431221172712215867119133061711912511912511201148122133612215241218120211171251301163351", solution8);
		redirects.put("164111633511828631116561287731146988122125122114912211491399114913991261221122088612213512211653122112912214601221119311481959114811261", "1481165712211677122146012214201221163431221172712215867119133061711912511912511201148122133612215241218120211171251301163351");
		redirects.put("550137152131107461127641415996112811301100224116414011479621221321221321221321221321221321162911000116201753116271386311627116611160091290814691", "1481165712211677122146012214201221163431221172712215867119133061711912511912511201148122133612215241218120211171251301163351");
		redirects.put("15601114813593125136417511491801773144312430111902144461161751275411324323122116092228146629115114031151140311511163111511502712654216221417116261", "1481165712211677122146012214201221163431221172712215867119133061711912511912511201148122133612215241218120211171251301163351");
		
	} //tpb
	
	private static void registerL() {
		
		SecretSolution solution0 = new SecretSolution();
		solution0.addSecret(new SecretChest(-32, 52, 11));
		solution0.addSecret(new SecretMisc(-25, 57, -4));
		solution0.addSecret(new SecretBat(14, 47, -5));
		solution0.addSecret(new SecretWall(-2, 93, -5));
		solution0.addSecret(new SecretChest(-2, 92, -11));
		solutions.put("1352761-1930761689134610114167641211991322813862110011380503693149415731511011225411971352811971175001991225411862149012865814951144111146131343541166181112009930612096317493378001125859111028115721862901", solution0);
		redirects.put("2399412486761164061191771124608496616511749772123311322113591320415511341110115631149213028514813618148151481150164531241560712412735112751243451167161249215991", "1352761-1930761689134610114167641211991322813862110011380503693149415731511011225411971352811971175001991225411862149012865814951144111146131343541166181112009930612096317493378001125859111028115721862901");
		redirects.put("4701511-16859822791387114321729351387816197989514811180820161231531916171270481450214060140235133301316471422112473612457161551159316681111602129330189142841642356411557128454491314311294215151", "1352761-1930761689134610114167641211991322813862110011380503693149415731511011225411971352811971175001991225411862149012865814951144111146131343541166181112009930612096317493378001125859111028115721862901");
		
		SecretSolution solution1 = new SecretSolution();
		solution1.addSecret(new SecretWall(17, 70, 3));
		solution1.addSecret(new SecretChest(17, 69, -4));
		solution1.addSecret(new SecretChest(7, 57, 45));
		solution1.addPathPoint(9, 68, 43);
		solution1.addSecret(new SecretWall(-24, 82, 17));
		solution1.addSecret(new SecretChest(-38, 83, 7));
		solution1.addSecret(new SecretLever(-3, 71, 47));
		solution1.addPathPoint(-24, 82, 17);
		solution1.addPathPoint(7, 81, 10);
		solution1.addPathPoint(-1, 79, 47);
		solution1.addSecret(new SecretBat(17, 83, 26));
		solution1.addSecret(new SecretChest(0, 69, -1));
		solution1.addPathPoint(-25, 70, -4);
		solution1.addPathPoint(0, 70, -5);
		solutions.put("21011981135119811687965417171277152913111297117521393118601166129112471389116512761789141000501184715176321228143136217371739951431310347615019106811101", solution1);
		redirects.put("168135131110013113413119913113539661331401321401331401362331391363391351244135111612041", "21011981135119811687965417171277152913111297117521393118601166129112471389116512761789141000501184715176321228143136217371739951431310347615019106811101");
		redirects.put("1411133143113014915281521625139136113751235127213913811041184135130613613311391202271371221137188712331331779112718061", "21011981135119811687965417171277152913111297117521393118601166129112471389116512761789141000501184715176321228143136217371739951431310347615019106811101");
	
		SecretSolution solution2 = new SecretSolution();
		solution2.addSecret(new SecretWall(1, 70, 28));
		solution2.addSecret(new SecretChest(5, 69, 28));
		solution2.addSecret(new SecretWall(-3, 78, 49));
		solution2.addSecret(new SecretLever(-3, 76, 52));
		solution2.addSecret(new SecretChest(-3, 69, 52));
		solution2.addSecret(new SecretMisc(17, 70, 26));
		solution2.addSecret(new SecretChest(46, 78, -2));
		solution2.addSecret(new SecretWall(39, 80, 12));
		solution2.addSecret(new SecretMisc(39, 79, 20));
		solution2.addSecret(new SecretLever(0, 91, 22));
		solution2.addSecret(new SecretMisc(-1, 96, -2));
		solution2.addSecret(new SecretChest(39, 69, 7));
		solution2.addSecret(new SecretMisc(27, 59, 2));
		solution2.addPathPoint(27, 69, -3);
		solutions.put("90219951998140001105817041527513510161217991900148061449014489144901258313510150231995177017981260214490139991122292140481100081707160515111400018041", solution2);
		redirects.put("29961109719263171856015538140481175719681252811062114294558130231455718031132061804110399144891113661803140081901110961465411627112621106213616111601360711160113031", "90219951998140001105817041527513510161217991900148061449014489144901258313510150231995177017981260214490139991122292140481100081707160515111400018041");
		redirects.put("7956116811501611913182086117181895861898121684111911687111324159821158212462412008614806116801275341190081446411582114891138711582115841211421138711192115201648962281111291", "90219951998140001105817041527513510161217991900148061449014489144901258313510150231995177017981260214490139991122292140481100081707160515111400018041");
		
		SecretSolution solution3 = new SecretSolution();
		solution3.addSecret(new SecretChest(7, 89, 52));
		solution3.addSecret(new SecretMisc(-4, 91, 51));
		solution3.addSecret(new SecretWall(-7, 88, -4));
		solution3.addSecret(new SecretChest(-15, 91, 10));
		solution3.addSecret(new SecretChest(10, 88, 3));
		solution3.addSecret(new SecretMisc(19, 95, -6));
		solution3.addPathPoint(15, 89, -1);
		solution3.addSecret(new SecretMisc(16, 95, -1));
		solution3.addSecret(new SecretMisc(-24, 57, 6));
		solutions.put("3570170561308512621371512911084140119391291810113318101131184013461683011351137312717504127120917117301891512718915129114411331129218701", solution3);
		redirects.put("1351294113512941833018461263801411612150142661208017101416128118012612080128143311301209184611401786114013264129132641145117412911861", "3570170561308512621371512911084140119391291810113318101131184013461683011351137312717504127120917117301891512718915129114411331129218701");
		redirects.put("511351421153151110051137127911445191013913713913611195123111911141128111851292127912181181142012611564151801934176613906132901", "3570170561308512621371512911084140119391291810113318101131184013461683011351137312717504127120917117301891512718915129114411331129218701");
		
		SecretSolution solution4 = new SecretSolution();
		solution4.addSecret(new SecretChest(0, 28, 14));
		solution4.addPathPoint(0, 48, 0);
		solution4.addSecret(new SecretChest(-36, 50, 20));
		solution4.addPathPoint(-34, 51, 11);
		solution4.addSecret(new SecretMisc(-2, 90, 19));
		solution4.addPathPoint(-12, 55, 9);
		solution4.addPathPoint(-25, 69, 16);
		solution4.addPathPoint(-2, 73, -2);
		solution4.addPathPoint(-26, 77, -2);
		solution4.addPathPoint(-14, 83, -2);
		solution4.addPathPoint(-1, 91, 16);
		solution4.addSecret(new SecretChest(-9, 92, 7));
		solution4.addSecret(new SecretMisc(-6, 92, 18));
		solution4.addPathPoint(-9, 96, 12);
		solution4.addSecret(new SecretLever(-8, 93, 19));
		solution4.addSecret(new SecretChest(-8, 84, 19));
		solution4.addSecret(new SecretChest(-4, 84, 41));
		solution4.addPathPoint(9, 83, 24);
		solution4.addPathPoint(3, 84, 48);
		solution4.addSecret(new SecretBat(-6, 85, 46));
		solution4.addSecret(new SecretWall(15, 84, 19));
		solution4.addSecret(new SecretChest(16, 83, 2));
		solutions.put("651275115111710321512414772141294117378516019772214211857011035193701685135961134051501330514115531110401215111212081174145116015921171189112812461", solution4);
		redirects.put("14421233281111221111139043971131685149110101910115391011309861011106541015411431813612521343781328612491", "651275115111710321512414772141294117378516019772214211857011035193701685135961134051501330514115531110401215111212081174145116015921171189112812461");
		redirects.put("213551354011624301149651905518818070112871861395112711102135123512599126811963812181686493519521329159314891773012238181325586611830914330371268711094151817923511261", "651275115111710321512414772141294117378516019772214211857011035193701685135961134051501330514115531110401215111212081174145116015921171189112812461");
		
		SecretSolution solution5 = new SecretSolution();
		solution5.addSecret(new SecretBat(4, 63, 19));
		solution5.addPathPoint(0, 67, 20);
		solution5.addSecret(new SecretMisc(7, 51, 22));
		solution5.addPathPoint(12, 51, 17);
		solution5.addSecret(new SecretWall(19, 68, -3));
		solution5.addPathPoint(11, 51, 18);
		solution5.addPathPoint(25, 67, 2);
		solution5.addSecret(new SecretChest(15, 67, -3));
		solution5.addSecret(new SecretChest(50, 86, 7));
		solution5.addPathPoint(34, 86, 7);
		solution5.addSecret(new SecretLever(0, 84, 12));
		solution5.addSecret(new SecretChest(21, 88, 47));
		solution5.addPathPoint(5, 83, 14);
		solution5.addPathPoint(-2, 88, 21);
		solution5.addPathPoint(0, 88, 46);
		solution5.addSecret(new SecretChest(21, 88, 45));
		solutions.put("1961368137056119613681368611113681366641756930315181224501-61374746915181224191368135881151812241716134624131-117609245013681358811146678030118406794011513681358831013681358831012083213903101-19948950091016724101394101296101", solution5);
		redirects.put("1840679602101-235218377101199137846495115331-187281398211031-1994889848181-13845171181-11760923971-199489033815181224191-19948903381-21169649791-2116964986114491-1994890338114411-19948888681143921368135881118101-77413678313681358871928301632136813598911056221911840679604131", "1961368137056119613681368611113681366641756930315181224501-61374746915181224191368135881151812241716134624131-117609245013681358811146678030118406794011513681358831013681358831012083213903101-19948950091016724101394101296101");
		redirects.put("013681358811696136813588118911184067940510136813591910136813591411881-613753089101-6137531081113681358811-191843393613681358881-61374746913681358881-981742953116227106451-98174295310118406808491013681373281013681359141013681358831-13931519811", "1961368137056119613681368611113681366641756930315181224501-61374746915181224191368135881151812241716134624131-117609245013681358811146678030118406794011513681358831013681358831012083213903101-19948950091016724101394101296101");
		
		SecretSolution solution6 = new SecretSolution();
		solution6.addSecret(new SecretChest(-36, 82, -3));
		solution6.addPathPoint(-36, 69, 7);
		solution6.addPathPoint(-36, 80, 4);
		solution6.addSecret(new SecretChest(-39, 79, 18));
		solution6.addPathPoint(-28, 78, 18);
		solution6.addSecret(new SecretWall(1, 70, -5));
		solution6.addSecret(new SecretBat(-13, 74, -1));
		solution6.addSecret(new SecretChest(-5, 69, 7));
		solution6.addSecret(new SecretChest(13, 80, 25));
		solution6.addPathPoint(8, 79, 17);
		solution6.addSecret(new SecretChest(16, 85, 33));
		solution6.addPathPoint(17, 82, 42);
		solution6.addPathPoint(21, 85, 36);
		solution6.addSecret(new SecretChest(18, 69, 51));
		solution6.addPathPoint(20, 71, 43);
		solutions.put("5121301354561195134125249198212081116121011131207188413031120130711161308188013061120150011201597159013712311894112511133612512111251", solution6);
		redirects.put("3181241271911158124147719882121144361123181121115118115123115121115186118123118179111159151211355151211217131817991", "5121301354561195134125249198212081116121011131207188413031120130711161308188013061120150011201597159013712311894112511133612512111251");
		redirects.put("157921124110655334113015085113011593113011513311512711512711511159531151141889115118220115117199512114378513641123127812512801", "5121301354561195134125249198212081116121011131207188413031120130711161308188013061120150011201597159013712311894112511133612512111251");
		
		SecretSolution solution7 = new SecretSolution();
		solution7.addSecret(new SecretWall(-5, 73, 19));
		solution7.addSecret(new SecretChest(5, 55, 51));
		solution7.addSecret(new SecretWall(-35, 70, 18));
		solution7.addSecret(new SecretLever(-37, 61, 16));
		solution7.addSecret(new SecretChest(-25, 111, 21));
		solution7.addSecret(new SecretMisc(-25, 111, -7));
		solution7.addSecret(new SecretChest(-20, 104, 7));
		solutions.put("2323871931191121193121193219112111911750126311014110601780215653132574122380145031551511240150001102712998514523126135199012610111581", solution7);
		redirects.put("122123112212318881231221123115412311541231270123117121117154170119311121193118112312791231115119311151825157511931", "2323871931191121193121193219112111911750126311014110601780215653132574122380145031551511240150001102712998514523126135199012610111581");
		redirects.put("101638110198811021103120014182419881419881147819881758198818119814130135110216481200132616126316129851118381282120021", "2323871931191121193121193219112111911750126311014110601780215653132574122380145031551511240150001102712998514523126135199012610111581");
		
		SecretSolution solution8 = new SecretSolution();
		solution8.addSecret(new SecretChest(-24, 79, 8));
		solution8.addPathPoint(-16, 80, 10);
		solution8.addSecret(new SecretWall(16, 67, 12));
		solution8.addSecret(new SecretBat(13, 62, 12));
		solution8.addSecret(new SecretMisc(15, 59, 4));
		solution8.addSecret(new SecretChest(6, 86, 16));
		solution8.addPathPoint(16, 69, -2);
		solution8.addPathPoint(16, 75, -16);
		solution8.addPathPoint(16, 86, 16);
		solution8.addSecret(new SecretChest(-10, 61, 8));
		solution8.addPathPoint(-10, 67, -6);
		solutions.put("681472168124616819201232124125153127110011251100112511151251125115611045141912016392110412898118162711712271708132417121", solution8);
		redirects.put("33117213311346141813102112713699512261237331131144811311291311129113112911281251514114413191181113511201420155114721160571147211351", "681472168124616819201232124125153127110011251100112511151251125115611045141912016392110412898118162711712271708132417121");
		redirects.put("161118601611906011261927112561159132312511281281128125163211251632125113211561223132212231797612241115561611799201611226161127921", "681472168124616819201232124125153127110011251100112511151251125115611045141912016392110412898118162711712271708132417121");
		
		SecretSolution solution9 = new SecretSolution();
		solution9.addSecret(new SecretChest(17, 84, 38));
		solution9.addPathPoint(16, 60, 35);
		solution9.addPathPoint(17, 63, 49);
		solution9.addPathPoint(17, 84, 46);
		solution9.addSecret(new SecretMisc(18, 60, -4));
		solution9.addSecret(new SecretMisc(-36, 60, 11));
		solution9.addPathPoint(-17, 60, -3);
		solution9.addPathPoint(-33, 60, -1);
		solution9.addSecret(new SecretLever(-21, 82, 7));
		solution9.addSecret(new SecretChest(20, 83, 9));
		solution9.addSecret(new SecretWall(1, 61, 8));
		solution9.addSecret(new SecretBat(6, 62, 9));
		solution9.addSecret(new SecretChest(6, 60, 7));
		solutions.put("241811198110051541316411791820162517041272151012161901122018021281190013031705186318021229137061149114011149122701181781211181", solution9);
		redirects.put("61112411498561241136012418211167131316311165113181307115013321150121611501411115015091631111931167150941241429912414299113516211161", "241811198110051541316411791820162517041272151012161901122018021281190013031705186318021229137061149114011149122701181781211181");
		redirects.put("7151241988775124135911580912043312071150051103281631612241319011223126675812231322667128017935411861792721279179353127170613191271102412819748128199841", "241811198110051541316411791820162517041272151012161901122018021281190013031705186318021229137061149114011149122701181781211181");
		
		SecretSolution solution10 = new SecretSolution();
		solution10.addSecret(new SecretMisc(6, 71, 8));
		solution10.addSecret(new SecretChest(11, 83, 3));
		solution10.addSecret(new SecretMisc(27, 93, 5));
		solution10.addPathPoint(40, 77, -2);
		solution10.addPathPoint(51, 82, 5);
		solution10.addPathPoint(36, 94, -3);
		solution10.addSecret(new SecretChest(9, 88, -29));
		solution10.addPathPoint(40, 86, 2);
		solution10.addPathPoint(19, 90, -9);
		solution10.addSecret(new SecretBat(19, 53, -14));
		solution10.addPathPoint(7, 60, -26);
		solution10.addPathPoint(10, 56, -10);
		solution10.addPathPoint(16, 57, -8);
		solution10.addSecret(new SecretChest(15, 44, 11));
		solution10.addPathPoint(11, 54, -3);
		solution10.addPathPoint(-5, 55, -12);
		solution10.addPathPoint(8, 48, -31);
		solution10.addPathPoint(15, 44, -19);
		solutions.put("8614821161936114918111491811149182114811671148147001148120571282113111813371152193115319311801821158018211732191216115991901", solution10);
		redirects.put("12133112133112128459125011481121251121162311211021121243631714980154361130231-2665512281128971-1269058818113031554723575114617271380271741-11090891961201-1162428656120232831", "8614821161936114918111491811149182114811671148147001148120571282113111813371152193115319311801821158018211732191216115991901");
		redirects.put("6541365129147071291158412512070126471158415881171172217114141171231285123117112321179149118131851181233739114411998014012011821", "8614821161936114918111491811149182114811671148147001148120571282113111813371152193115319311801821158018211732191216115991901");
		
	} //tpl
	
}
