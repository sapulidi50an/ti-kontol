// File: boss/Boss.java - KODE YANG SUDAH DIPERBAIKI
package boss;
import java.util.Random;

public class Boss extends Musuh {
    private boolean enraged;
    private int originalAttack;
    private int skillCooldown;
    private static final int ENRAGE_THRESHOLD = 50;
    
    public Boss(String nama) {
        super(nama, 10); // Level 10 boss
        
        // Override stats untuk boss yang lebih kuat
        this.maxHp = 500;
        this.hp = maxHp;
        this.attack = 50;
        this.originalAttack = attack;
        this.defense = 25;
        this.expReward = 1000;
        this.enraged = false;
        this.skillCooldown = 0;
        this.kelas = "Boss";
    }
    
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        
        // Boss menjadi enraged ketika HP < 50%
        if (!enraged && hp < (maxHp * ENRAGE_THRESHOLD / 100)) {
            enraged = true;
            attack += 20;
            System.out.println("🔥 " + nama + " menjadi ENRAGED! Attack meningkat dari " + 
                             originalAttack + " menjadi " + attack + "!");
        }
    }
    
    @Override
    public void useSpecialSkill(Musuh target) {  // Parameter tetap Musuh sesuai parent class
        if (skillCooldown > 0) {
            System.out.println("⏳ " + nama + " masih dalam cooldown skill!");
            skillCooldown--;
            return;
        }
        
        Random rand = new Random();
        int skillChoice = rand.nextInt(3);
        
        switch (skillChoice) {
            case 0:
                devastation(target);
                skillCooldown = 2;
                break;
            case 1:
                fortress();
                skillCooldown = 1;
                break;
            case 2:
                regeneration();
                skillCooldown = 3;
                break;
        }
    }
    
    // PERBAIKAN UTAMA: Hapus instanceof check yang salah
    private void devastation(Musuh target) {
        int baseDamage = attack * 2;
        int actualDamage = enraged ? (int)(baseDamage * 1.5) : baseDamage;
        
        // Langsung panggil takeDamage - semua Musuh inherit dari Pahlawan
        target.takeDamage(actualDamage);
        System.out.println("💀 " + nama + " menggunakan DEVASTATION! " + 
                         (enraged ? "(ENRAGED BONUS!) " : "") + 
                         "Damage: " + actualDamage);
    }
    
    private void fortress() {
        int defenseBoost = enraged ? 15 : 10;
        defense += defenseBoost;
        System.out.println("🛡️ " + nama + " menggunakan FORTRESS! Defense meningkat sebesar " + 
                         defenseBoost + " (Total: " + defense + ")");
    }
    
    private void regeneration() {
        int healAmount = enraged ? 75 : 50;
        int oldHp = hp;
        
        // Menggunakan method heal() dari parent class
        heal(healAmount);
        int actualHeal = hp - oldHp;
        
        System.out.println("💚 " + nama + " menggunakan REGENERATION! " + 
                         (enraged ? "(ENRAGED BONUS!) " : "") + 
                         "Heal: " + actualHeal + " HP (" + hp + "/" + maxHp + ")");
    }
    
    public void reduceCooldown() {
        if (skillCooldown > 0) {
            skillCooldown--;
        }
    }
    
    @Override
    public void displayStatus() {
        System.out.println("=== " + nama + " (" + kelas + ") ===");
        System.out.println("Level: " + level + " | HP: " + hp + "/" + maxHp + 
                         " (" + (hp * 100 / maxHp) + "%)");
        System.out.println("ATK: " + attack + 
                         (enraged ? " (ENRAGED!)" : "") + " | DEF: " + defense);
        System.out.println("State: " + (enraged ? "🔥 ENRAGED" : "😐 Normal"));
        if (skillCooldown > 0) {
            System.out.println("Skill Cooldown: " + skillCooldown + " turns");
        }
        System.out.println("EXP Reward: " + expReward);
        System.out.println("Skills: " + skills);
        System.out.println("===================");
    }
    
    @Override
    protected void initializeSkills() {
        // Boss memiliki skill khusus
        skills.add("Devastation");
        skills.add("Fortress");
        skills.add("Regeneration");
    }

    // Getters and setters
    public boolean isEnraged() {
        return enraged;
    }

    public void setEnraged(boolean enraged) {
        this.enraged = enraged;
    }

    public int getOriginalAttack() {
        return originalAttack;
    }

    public void setOriginalAttack(int originalAttack) {
        this.originalAttack = originalAttack;
    }

    public int getSkillCooldown() {
        return skillCooldown;
    }

    public void setSkillCooldown(int skillCooldown) {
        this.skillCooldown = skillCooldown;
    }

    public static int getEnrageThreshold() {
        return ENRAGE_THRESHOLD;
    }
    
    @Override
    public void levelUp() {
        super.levelUp();
        
        // Tambah kemampuan boss saat level up
        this.maxHp += 50;
        this.hp = maxHp;
        this.attack += 10;
        this.defense += 5;
        this.expReward += 100;
        
        System.out.println(nama + " naik level! Stats meningkat.");
    }
}