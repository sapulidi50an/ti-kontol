// File: boss/Musuh.java
package boss;
import pahlawan.Pahlawan;

public class Musuh extends Pahlawan {
    protected int expReward;
    
    public Musuh(String nama, int level) {
        super(nama, "Monster");
        this.level = level;
        
        // Scale stats based on level
        this.maxHp = 60 + (level * 20);
        this.hp = maxHp;
        this.attack = 15 + (level * 3);
        this.defense = 5 + (level * 2);
        this.expReward = level * 25;
    }
    
    @Override
    protected void initializeSkills() {
        skills.add("Serangan Dasar");
    }
    
    @Override
    protected void learnNewSkill() {
        // Musuh tidak belajar skill baru
    }
    
    @Override
    public void useSpecialSkill(Musuh target) {
        // Musuh menggunakan serangan khusus
        int damage = attack + 5;
        if (target instanceof Pahlawan) {
            ((Pahlawan) target).takeDamage(damage);
            System.out.println("👹 " + nama + " menggunakan serangan khusus! Damage: " + damage);
        }
    }
    
    public void attackPlayer(Pahlawan player) {
        int damage = Math.max(1, attack - player.getDefense());
        player.takeDamage(damage);
        System.out.println("👹 " + nama + " menyerang " + player.getNama() + " sebesar " + damage + " damage!");
    }
    
    public int getExpReward() {
        return expReward;
    }

    public int getDefense() {
        return this.defense;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
    }

    public String getNama() {
        return this.nama;
    }
}
