package TheLightbearer;


import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomScreen;
import basemod.interfaces.*;
import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.potions.BasePotion;
import TheLightbearer.relics.BaseRelic;
import TheLightbearer.util.GeneralUtils;
import TheLightbearer.util.KeywordInfo;
import TheLightbearer.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import com.badlogic.gdx.graphics.Color;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static basemod.BaseMod.addCustomScreen;


@SpireInitializer
public class TheLightbearer implements
        AddAudioSubscriber,
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber{
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    public static SpireConfig lightbearerConfig;
    private static final String resourcesFolder = "TheLightbearer";

    private static final String BG_ATTACK = characterPath("cardback/none/attack_none.png");
    private static final String BG_ATTACK_P = characterPath("cardback/none/attack_none_p.png");
    private static final String BG_SKILL = characterPath("cardback/none/skill_none.png");
    private static final String BG_SKILL_P = characterPath("cardback/none/skill_none_p.png");
    private static final String BG_POWER = characterPath("cardback/none/power_none.png");
    private static final String BG_POWER_P = characterPath("cardback/none/power_none_p.png");
    private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
    private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
    private static final String SMALL_ORB = characterPath("cardback/small_orb.png");
    private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
    private static final String CHAR_SELECT_PORTRAIT = characterPath("select/portrait.png");

    private static final Color cardColor = new Color(254f/255f, 254f/255f, 215f/255f, 1f);

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {

        new TheLightbearer();

        BaseMod.addColor(LightbearerCharacter.Enums.CARD_COLOR, cardColor,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
    }

    public TheLightbearer() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
        Properties lightbearerDefaults = new Properties();
        lightbearerDefaults.setProperty("Charge Tutorial Seen","FALSE");
        try{
            lightbearerConfig = new SpireConfig("The Lightbearer","TheLightbearer",lightbearerDefaults);
        }catch (IOException e){
            logger.error("TheLightbearer SpireConfig initialization failed:");
            e.printStackTrace();
        }
        logger.info("LIGHTBEARER CONFIG OPTIONS LOADED:");
        logger.info("Charge tutorial seen: " + lightbearerConfig.getString("Charge Tutorial Seen") + ".");
    }

    @Override
    public void receivePostInitialize() {
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);
        registerPotions();
        //registerCustomRewards();

       // BaseMod.addCustomScreen(new TutorialScreen());

    }



    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
        BaseMod.loadCustomStringsFile(TutorialStrings.class,
                localizationPath(lang, "TutorialStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }


    //This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(TheLightbearer.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new LightbearerCharacter(),
                CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT, LightbearerCharacter.Enums.THE_LIGHTBEARER);
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards
    }

    @Override
    public void receiveEditRelics() {

        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }
    private static void registerPotions() {
    new AutoAdd(modID)
        .packageFilter(BasePotion.class)
            .any(BasePotion.class,(info, potion) ->{
                BaseMod.addPotion(potion.getClass(),null,null,null, potion.ID, potion.playerClass);
            });
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio("GoldenGunSFX", "TheLightbearer/audio/GoldenGunSFX.ogg");
        BaseMod.addAudio("GoldenGunCast", "TheLightbearer/audio/GoldenGunCast.ogg");
        BaseMod.addAudio("BladeBarrageCast", "TheLightbearer/audio/BladeBarrageCast.ogg");
        BaseMod.addAudio("ArcStaffCast", "TheLightbearer/audio/ArcStaffCast.ogg");
        BaseMod.addAudio("GatheringStormCast", "TheLightbearer/audio/GatheringStormCast.ogg");
        BaseMod.addAudio("ShadowshotCast", "TheLightbearer/audio/ShadowshotCast.ogg");
        BaseMod.addAudio("SpectralBladesCast", "TheLightbearer/audio/SpectralBladesCast.ogg");
    }
    /*public void registerCustomRewards(){
        BaseMod.registerCustomReward(
                SUPER_REWARD_TYPE,
                rewardSave -> new SuperReward(),
                customReward -> new RewardSave(customReward.type.toString(),null,0,0)
        );
     }

     */
}
