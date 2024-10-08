package TheLightbearer.cards;

import TheLightbearer.powers.ChargeOfLightPower;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.abstracts.DynamicVariable;
import TheLightbearer.TheLightbearer;
import TheLightbearer.util.CardStats;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static TheLightbearer.util.CustomTags.*;
import static TheLightbearer.util.GeneralUtils.removePrefix;
import static TheLightbearer.util.TextureLoader.getCardTextureString;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public abstract class BaseCard extends CustomCard {
    final private static Map<String, DynamicVariable> customVars = new HashMap<>();

    protected static String makeID(String name) { return TheLightbearer.makeID(name); }
    protected CardStrings cardStrings;

    protected boolean upgradesDescription;

    protected int baseCost;

    protected boolean upgradeCost;
    protected int costUpgrade;

    protected boolean upgradeDamage;
    protected boolean upgradeBlock;
    protected boolean upgradeMagic;

    protected int damageUpgrade;
    protected int blockUpgrade;
    protected int magicUpgrade;

    protected boolean baseExhaust = false;
    protected boolean upgExhaust = false;
    protected boolean baseEthereal = false;
    protected boolean upgEthereal = false;
    protected boolean baseInnate = false;
    protected boolean upgInnate = false;
    protected boolean baseRetain = false;
    protected boolean upgRetain = false;

    final protected Map<String, LocalVarInfo> cardVariables = new HashMap<>();

    public BaseCard(String ID, CardStats info) {
        this(ID, info.baseCost, info.cardType, info.cardTarget, info.cardRarity, info.cardColor, "none");
    }
    public BaseCard(String ID, CardStats info, String element) {
        this(ID, info.baseCost, info.cardType, info.cardTarget, info.cardRarity, info.cardColor, element);
    }
    public BaseCard(String ID, CardStats info, boolean upgradesDescription, String element) {
        this(ID, info.baseCost, info.cardType, info.cardTarget, info.cardRarity, info.cardColor, upgradesDescription, element);
    }
    public BaseCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, String element)
    {
        super(ID, getName(ID), getCardTextureString(removePrefix(ID), cardType), cost, getInitialDescription(ID), cardType, color, rarity, target);
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);
        this.originalName = cardStrings.NAME;

        this.baseCost = cost;

        this.upgradesDescription = cardStrings.UPGRADE_DESCRIPTION != null;
        this.upgradeCost = false;
        this.upgradeDamage = false;
        this.upgradeBlock = false;
        this.upgradeMagic = false;

        this.costUpgrade = cost;
        this.damageUpgrade = 0;
        this.blockUpgrade = 0;
        this.magicUpgrade = 0;
        setBackgroundTexture("TheLightbearer/character/character/cardback/" + element + "/" + type.toString().toLowerCase() + "_" + element + ".png",
                "TheLightbearer/character/character/cardback/" + element + "/" + type.toString().toLowerCase() + "_" + element +  "_p.png");
    }
    public BaseCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, boolean upgradesDescription, String element)
    {
        this(ID, cost, cardType, target, rarity, color, element);
        this.upgradesDescription = upgradesDescription;
    }
    public BaseCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, boolean upgradesDescription)
    {
        this(ID, cost, cardType, target, rarity, color, "none");
        this.upgradesDescription = upgradesDescription;
    }

    private static String getName(String ID) {
        return CardCrawlGame.languagePack.getCardStrings(ID).NAME;
    }
    private static String getInitialDescription(String ID) {
        return CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
    }

    //Methods meant for constructor use
    protected final void setDamage(int damage)
    {
        this.setDamage(damage, 0);
    }
    protected final void setDamage(int damage, int damageUpgrade)
    {
        this.baseDamage = this.damage = damage;
        if (damageUpgrade != 0)
        {
            this.upgradeDamage = true;
            this.damageUpgrade = damageUpgrade;
        }
    }

    protected final void setBlock(int block)
    {
        this.setBlock(block, 0);
    }
    protected final void setBlock(int block, int blockUpgrade)
    {
        this.baseBlock = this.block = block;
        if (blockUpgrade != 0)
        {
            this.upgradeBlock = true;
            this.blockUpgrade = blockUpgrade;
        }
    }

    protected final void setMagic(int magic)
    {
        this.setMagic(magic, 0);
    }
    protected final void setMagic(int magic, int magicUpgrade)
    {
        this.baseMagicNumber = this.magicNumber = magic;
        if (magicUpgrade != 0)
        {
            this.upgradeMagic = true;
            this.magicUpgrade = magicUpgrade;
        }
    }

    protected final void setCustomVar(String key, int base) {
        this.setCustomVar(key, base, 0);
    }
    protected final void setCustomVar(String key, int base, int upgrade) {
        if (!customVars.containsKey(key)) {
            QuickDynamicVariable var = new QuickDynamicVariable(key);
            customVars.put(key, var);
            BaseMod.addDynamicVariable(var);
        }
        cardVariables.put(key, new LocalVarInfo(base, upgrade));
    }

    private LocalVarInfo getCustomVar(String key) {
        return cardVariables.get(key);
    }

    protected void calculateVarAsDamage(String key) {
        setVarCalculation(key, (m, base)->{
            int tmp = this.baseDamage;

            this.baseDamage = base;
            if (m != null)
                super.calculateCardDamage(m);
            else
                super.applyPowers();

            this.baseDamage = tmp;
            return damage;
        });
    }
    protected void calculateVarAsBlock(String key) {
        setVarCalculation(key, (m, base)->{
            int tmp = this.baseBlock;

            this.baseBlock = base;
            if (m != null)
                super.calculateCardDamage(m);
            else
                super.applyPowers();

            this.baseBlock = tmp;
            return block;
        });
    }
    protected void setVarCalculation(String key, BiFunction<AbstractMonster, Integer, Integer> calculation) {
        cardVariables.get(key).calculation = calculation;
    }

    public int customVarBase(String key) {
        LocalVarInfo var = cardVariables.get(key);
        if (var == null)
            return -1;
        return var.base;
    }
    public int customVar(String key) {
        LocalVarInfo var = cardVariables.get(key);
        if (var == null)
            return -1;
        return var.value;
    }
    public boolean isCustomVarModified(String key) {
        LocalVarInfo var = cardVariables.get(key);
        if (var == null)
            return false;
        return var.isModified();
    }
    public boolean customVarUpgraded(String key) {
        LocalVarInfo var = cardVariables.get(key);
        if (var == null)
            return false;
        return var.upgraded;
    }


    protected final void setCostUpgrade(int costUpgrade)
    {
        this.costUpgrade = costUpgrade;
        this.upgradeCost = true;
    }
    protected final void setExhaust(boolean exhaust) { this.setExhaust(exhaust, exhaust); }
    protected final void setEthereal(boolean ethereal) { this.setEthereal(ethereal, ethereal); }
    protected final void setInnate(boolean innate) {this.setInnate(innate, innate); }
    protected final void setSelfRetain(boolean retain) {this.setSelfRetain(retain, retain); }
    protected final void setExhaust(boolean baseExhaust, boolean upgExhaust)
    {
        this.baseExhaust = baseExhaust;
        this.upgExhaust = upgExhaust;
        this.exhaust = baseExhaust;
    }
    protected final void setEthereal(boolean baseEthereal, boolean upgEthereal)
    {
        this.baseEthereal = baseEthereal;
        this.upgEthereal = upgEthereal;
        this.isEthereal = baseEthereal;
    }
    protected void setInnate(boolean baseInnate, boolean upgInnate)
    {
        this.baseInnate = baseInnate;
        this.upgInnate = upgInnate;
        this.isInnate = baseInnate;
    }
    protected void setSelfRetain(boolean baseRetain, boolean upgRetain)
    {
        this.baseRetain = baseRetain;
        this.upgRetain = upgRetain;
        this.selfRetain = baseRetain;
    }


    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();

        if (card instanceof BaseCard)
        {
            card.rawDescription = this.rawDescription;
            ((BaseCard) card).upgradesDescription = this.upgradesDescription;

            ((BaseCard) card).baseCost = this.baseCost;

            ((BaseCard) card).upgradeCost = this.upgradeCost;
            ((BaseCard) card).upgradeDamage = this.upgradeDamage;
            ((BaseCard) card).upgradeBlock = this.upgradeBlock;
            ((BaseCard) card).upgradeMagic = this.upgradeMagic;

            ((BaseCard) card).costUpgrade = this.costUpgrade;
            ((BaseCard) card).damageUpgrade = this.damageUpgrade;
            ((BaseCard) card).blockUpgrade = this.blockUpgrade;
            ((BaseCard) card).magicUpgrade = this.magicUpgrade;

            ((BaseCard) card).baseExhaust = this.baseExhaust;
            ((BaseCard) card).upgExhaust = this.upgExhaust;
            ((BaseCard) card).baseEthereal = this.baseEthereal;
            ((BaseCard) card).upgEthereal = this.upgEthereal;
            ((BaseCard) card).baseInnate = this.baseInnate;
            ((BaseCard) card).upgInnate = this.upgInnate;
            ((BaseCard) card).baseRetain = this.baseRetain;
            ((BaseCard) card).upgRetain = this.upgRetain;

            for (Map.Entry<String, LocalVarInfo> varEntry : cardVariables.entrySet()) {
                LocalVarInfo target = ((BaseCard) card).getCustomVar(varEntry.getKey()),
                        current = varEntry.getValue();
                if (target == null) {
                    ((BaseCard) card).setCustomVar(varEntry.getKey(), current.base, current.upgrade);
                    target = ((BaseCard) card).getCustomVar(varEntry.getKey());
                }
                target.base = current.base;
                target.value = current.value;
                target.upgrade = current.upgrade;
                target.calculation = current.calculation;
            }
        }

        return card;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            this.upgradeName();

            if (this.upgradesDescription)
            {
                if (cardStrings.UPGRADE_DESCRIPTION == null)
                {
                    TheLightbearer.logger.error("Card " + cardID + " upgrades description and has null upgrade description.");
                }
                else
                {
                    this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                }
            }

            if (upgradeCost)
            {
                if (isCostModified && this.cost < this.baseCost && this.cost >= 0) {
                    int diff = this.costUpgrade - this.baseCost; //how the upgrade alters cost
                    this.upgradeBaseCost(this.cost + diff);
                    if (this.cost < 0)
                        this.cost = 0;
                }
                else {
                    upgradeBaseCost(costUpgrade);
                }
            }

            if (upgradeDamage)
                this.upgradeDamage(damageUpgrade);

            if (upgradeBlock)
                this.upgradeBlock(blockUpgrade);

            if (upgradeMagic)
                this.upgradeMagicNumber(magicUpgrade);

            for (LocalVarInfo var : cardVariables.values()) {
                if (var.upgrade != 0) {
                    var.base += var.upgrade;
                    var.value = var.base;
                    var.upgraded = true;
                }
            }

            if (baseExhaust ^ upgExhaust)
                this.exhaust = upgExhaust;

            if (baseInnate ^ upgInnate)
                this.isInnate = upgInnate;

            if (baseEthereal ^ upgEthereal)
                this.isEthereal = upgEthereal;

            if (baseRetain ^ upgRetain)
                this.selfRetain = upgRetain;


            this.initializeDescription();
        }
    }

    boolean inCalc = false;
    @Override
    public void applyPowers() {
        if (!inCalc) {
            inCalc = true;
            for (LocalVarInfo var : cardVariables.values()) {
                var.value = var.calculation.apply(null, var.base);
            }
            inCalc = false;
        }

        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        if (!inCalc) {
            inCalc = true;
            for (LocalVarInfo var : cardVariables.values()) {
                var.value = var.calculation.apply(m, var.base);
            }
            inCalc = false;
        }

        super.calculateCardDamage(m);
    }

    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        renderBorderText(sb);
    }

    @Override
    public void renderInLibrary(SpriteBatch sb) {
        super.renderInLibrary(sb);
        if (!(SingleCardViewPopup.isViewingUpgrade && this.isSeen && !this.isLocked)) {
            renderBorderText(sb);
        }
    }

    public void renderBorderText(SpriteBatch sb) {
        renderBorderText(sb, this.current_x, this.current_y, 400, this.drawScale);
    }

    public void renderBorderText(SpriteBatch sb, float xPos, float yPos, float yOffsetBase, float scale) {
        renderBorderText(sb, xPos, yPos, yOffsetBase, scale, false);
    }

    public void renderBorderText(SpriteBatch sb, float xPos, float yPos, float yOffsetBase, float scale, boolean renderBottom){
        String text;
        Color color;
        if(this.tags.contains(VOID)){
            text = "Void-Infused";
            color = Settings.PURPLE_COLOR;
        }else if(this.tags.contains(SOLAR)){
            text = "Solar-Infused";
            color = Color.ORANGE;
        }else if(this.tags.contains(ARC)){
            text = "Arc-Infused";
            color = Color.CYAN;
        }else if(this.tags.contains(NONE)){
            text = "Non-Elemental";
            color = Settings.CREAM_COLOR.cpy();
        }else{
            text = null;
            color = Settings.CREAM_COLOR.cpy();
        }

        if(this.cardID.equals(makeID("MasterOfLight"))){
            text = "All Elements";
            color = Settings.CREAM_COLOR.cpy();
        }


        if (text != null) {
            float offsetY;
            BitmapFont font;
            if (this.isFlipped || this.isLocked || this.transparency <= 0.0F)
                return;
            font = FontHelper.cardTitleFont;
            if (renderBottom) {
                yOffsetBase *= -1;
                yOffsetBase += 15f;
            }
            offsetY = yOffsetBase * Settings.scale * scale / 2.0F;
            BitmapFont.BitmapFontData fontData = font.getData();
            float originalScale = fontData.scaleX;
            float scaleMulti = 0.8F;
            int length = text.length();
            if (length > 20) {
                scaleMulti -= 0.02F * (length - 20);
                if (scaleMulti < 0.5F)
                    scaleMulti = 0.5F;
            }
            fontData.setScale(scaleMulti * (scale * 0.85f));
            color.a = this.transparency;
            FontHelper.renderRotatedText(sb, font, text, xPos, yPos, 0.0F, offsetY, this.angle, true, color);
            fontData.setScale(originalScale);
        }
    }

    public boolean canPlay(AbstractCard card) {
        if (card.tags.contains(SUPERSPELL)) {
            for (AbstractPower p : player.powers) {
                if (p.ID.equals(makeID("ChargeOfLightPower"))) {
                    if(p.amount >= ChargeOfLightPower.SUPER_COST || (!ChargeOfLightPower.getFreeCharge().isEmpty() && ChargeOfLightPower.getFreeCharge().get(0) == true)){
                        if (!ChargeOfLightPower.getFreeCharge().isEmpty()){
                            ArrayList<Boolean> freeChargeCopy = ChargeOfLightPower.getFreeCharge();
                            freeChargeCopy.remove(0);
                            ChargeOfLightPower.setFreeCharge(freeChargeCopy);
                            //logger.info("got to the array reduce");
                            return super.canPlay(card);
                        }
                        return super.canPlay(card);

                    }
                    ArrayList<AbstractCard> cards = AbstractDungeon.actionManager.cardsPlayedThisCombat;

                    for(AbstractRelic r : player.relics){
                        //logger.info(r.toString());
                        if(r.relicId.equals(Necronomicon.ID) && card.cost >= 2 &&
                                !cards.isEmpty() && cards.get(cards.size()-1).tags.contains(SUPERSPELL)){
                            if(cards.size() >= 2 && cards.get(cards.size()-2).tags.contains(SUPERSPELL)){
                                return false;
                            }
                            return super.canPlay(card);
                        }
                    }
                    return false;
                }
            }
        }
        return super.canPlay(card);
    }

    private static class QuickDynamicVariable extends DynamicVariable {
        final String localKey, key;

        public QuickDynamicVariable(String key) {
            this.localKey = key;
            this.key = makeID(key);
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public void setIsModified(AbstractCard c, boolean v) {
            if (c instanceof BaseCard) {
                LocalVarInfo var = ((BaseCard) c).getCustomVar(localKey);
                if (var != null)
                    var.forceModified = v;
            }
        }

        @Override
        public boolean isModified(AbstractCard c) {
            return c instanceof BaseCard && ((BaseCard) c).isCustomVarModified(localKey);
        }

        @Override
        public int value(AbstractCard c) {
            return c instanceof BaseCard ? ((BaseCard) c).customVar(localKey) : 0;
        }

        @Override
        public int baseValue(AbstractCard c) {
            return c instanceof BaseCard ? ((BaseCard) c).customVarBase(localKey) : 0;
        }

        @Override
        public boolean upgraded(AbstractCard c) {
            return c instanceof BaseCard && ((BaseCard) c).customVarUpgraded(localKey);
        }
    }


    private static class LocalVarInfo {
        int base, value, upgrade;
        boolean upgraded = false;
        boolean forceModified = false;

        BiFunction<AbstractMonster, Integer, Integer> calculation = LocalVarInfo::noCalc;

        public LocalVarInfo(int base, int upgrade) {
            this.base = this.value = base;
            this.upgrade = upgrade;
        }

        private static int noCalc(AbstractMonster m, int base) {
            return base;
        }

        public boolean isModified() {
            return forceModified || base != value;
        }
    }
}