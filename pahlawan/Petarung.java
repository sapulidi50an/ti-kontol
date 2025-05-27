package pahlawan;
import boss.Musuh;  // Perbaiki import statement

public class Petarung extends Pahlawan {
    
    public Petarung(String nama) {
        super(nama, "Petarung");
        this.hp += 30;
        this.maxHp += 30;
        this.attack += 10;
        this.defense += 5;
    }
    
    @Override
    protected void initializeSkills() {
        skills.add("Pukulan Kuat");
    }
    
    @Override
    protected void learnNewSkill() {
        String[] availableSkills = {"Rage Mode", "Shield Bash", "Combo Strike", "Berserker"};
        if (skills.size() < 5) {
            String newSkill = availableSkills[(skills.size() - 1) % availableSkills.length];
            skills.add(newSkill);
            System.out.println("🔥 " + nama + " mempelajari skill baru: " + newSkill);
        }
    }
    
    @Override
    public void useSpecialSkill(Musuh target) {
        if (skills.contains("Rage Mode")) {
            int damage = attack * 2;
            target.takeDamage(damage);
            System.out.println("💢 " + nama + " menggunakan Rage Mode! Damage: " + damage);
        } else {
            int damage = attack + 10;
            target.takeDamage(damage);
            System.out.println("👊 " + nama + " menggunakan Pukulan Kuat! Damage: " + damage);
        }
    }
}