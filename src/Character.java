public class Character {
    private static double baseSPD;
    private String name;
    private int level = 1;
    private double maxHP;
    // maxHP = 100+(10*level);
    private double currentHP ;
    private double maxMana;
    private double currentMana;
    private double maxSPD;
    private double currentSPD;
    private sword sw;
    private shield sh;
    private int isSwordEquip = 0;
    private int isShieldEquip = 0;
    private double dmg;
    private double def;
    private boolean isUnconscious;
    private int currentExp;
    private int maxExp;

    Character(String name){
        this.name = name;
        maxHP = 100+(10*level);
        currentHP = maxHP;
        maxMana = 50+(2*level);
        currentMana = maxMana;
        baseSPD = 10;
        maxSPD = baseSPD*(0.1+0.03*level);
        isUnconscious = false;
    }
    private void updateStatus(){
        maxHP = 100+(10*level);
        maxMana = 50+(2*level);
        maxExp = 100*level;
        maxSPD = baseSPD*(0.1+0.03*level);
    }
    private void updateEquipment(){
        currentSPD = maxSPD - (sw.getDecreaseSPD()*isSwordEquip+sh.getDecreaseSPD()*isShieldEquip);
        dmg = sw.getAtk()*isSwordEquip;
        def = sh.getDef()*isShieldEquip;
    }
    private void characterLevelAscending(){
        level++;
        updateStatus();
    }
    public void swordEquip(sword s){
        this.sw = s;
        isSwordEquip = 1;
        updateEquipment();
    }
    public void shieldEquip(shield s){
        this.sh = s;
        isShieldEquip = 1;
        updateEquipment();
    }
    public void swordUnEquip(){
        isSwordEquip = 0;
        updateEquipment();
    }
    public void shieldUnEquip(){
        isShieldEquip = 0;
        updateEquipment();
    }
    public void updateSwordLevel(int exp){
        sw.updateExp(exp);
        updateEquipment();
    }
    public void updateShieldLevel(int exp){
        sh.updateExp(exp);
        updateEquipment();
    }
    public double wasAttacked(double damage){
        double damageTaken = damage - isShieldEquip*def ;
        currentHP = currentHP - damageTaken;
        if(currentHP <= 0 ){
            currentHP = 0;
            isUnconscious = true;
        }
        return damageTaken;
    }
    public double attack() {
        if(!isUnconscious) {
            if (currentMana != 0) {
                currentMana--;
                return dmg;
            }
            System.out.println("You run out of mana");
            return 0;
        }
        System.out.println("Character is unconscious");
        return 0;
    }

    public void healthPack(int blood){
        if(!isUnconscious) {
            if (currentMana >= (blood/maxHP*maxMana/3)) {
                currentMana = currentMana-(blood/maxHP*maxMana/3);
                currentHP = currentHP + blood;
            }
            System.out.println("You run out of mana");
        }
        currentHP = maxHP;
        currentMana = maxMana;
    }
    public void changeName(String n){
        this.name = n;
    }
    public void showStatus(){
        System.out.println("Name : "+this.name);
        System.out.println("Level : "+this.level + "exp" + currentExp + " / " + maxExp);
        System.out.println("HP : " + currentHP + " / " + maxHP);
        System.out.println("mana : " + currentMana + " / " + maxMana);
        System.out.println("speed : " + this.currentSPD);
        if(isSwordEquip == 1)sw.showStatus();
        if(isShieldEquip == 1)sh.showStatus();
    }
    public void updateExp(int exp){
        this.currentExp += exp;
        if(currentExp >= maxExp){
            currentExp -= maxExp;
            this.characterLevelAscending();
            System.out.println("Level up !!");
        }
    }

}
