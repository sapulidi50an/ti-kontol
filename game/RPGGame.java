// File: game/RPGGame.java
package game;
import boss.*;
import java.util.*;
import pahlawan.*;

public class RPGGame {  // Ubah dari Rpg ke RPGGame
    private Pahlawan player;
    private List<Musuh> enemies;
    private Boss finalBoss;
    private Scanner scanner;
    private Random random;
    
    public RPGGame() {  // Constructor yang benar (hapus void)
        scanner = new Scanner(System.in);
        random = new Random();
        initializeEnemies();
        finalBoss = new Boss("Dark Lord Malphas");
    }
    
    private void initializeEnemies() {
        enemies = new ArrayList<>();
        String[] enemyNames = {
            "Goblin Warrior", "Orc Berserker", "Skeleton Archer", 
            "Dark Mage", "Troll Brute", "Shadow Assassin",
            "Fire Elemental", "Ice Golem", "Poison Spider", "Dragon Whelp"
        };
        
        for (int i = 0; i < enemyNames.length; i++) {
            enemies.add(new Musuh(enemyNames[i], i + 1));
        }
    }
    
    public void startGame() {
        System.out.println(" ========== SELAMAT DATANG DI GAME DUNGEON SEEKER ========== ");
        System.out.println("Pilih class pahlawan Anda:");
        
        createCharacter();
        
        System.out.println("\n Anda memasuki dungeon yang gelap dan berbahaya...");
        System.out.println("Tujuan Anda: Kalahkan semua musuh untuk mendapatkan harta karun dan hadapi Boss terakhir!\n");
        
        // Battle through all enemies
        for (int i = 0; i < enemies.size(); i++) {
            if (!player.isAlive()) {
                gameOver();
                return;
            }
            
            System.out.println("\n⚔️ ========== PERTARUNGAN " + (i + 1) + " ==========");
            battle(enemies.get(i));
            
            if (player.isAlive()) {
                restBetweenBattles();
            }
        }
        
        // Final boss battle
        if (player.isAlive()) {
            System.out.println("\n🔥 ========== BOSS BATTLE ========== 🔥");
            System.out.println("Anda telah mencapai ruang terakhir...");
            System.out.println("Dark Lord Malphas muncul dengan aura yang menakutkan!");
            
            bossBattle(finalBoss);
            
            if (player.isAlive()) {
                victory();
            } else {
                gameOver();
            }
        }
    }
    
    private void createCharacter() {
        System.out.println("1.  Petarung (High HP & Defense)");
        System.out.println("2. Pemanah (High Attack & Speed)");
        System.out.println("3.  Penyihir (Magic & Healing)");
        System.out.print("Pilihan Anda (1-3): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        System.out.print("Masukkan nama pahlawan: ");
        String name = scanner.nextLine();
        
        switch (choice) {
            case 1:
                player = new Petarung(name);
                break;
            case 2:
                player = new Pemanah(name);
                break;
            case 3:
                player = new Penyihir(name);
                break;
            default:
                System.out.println("Pilihan tidak valid. Membuat Petarung secara default.");
                player = new Petarung(name);
        }
        
        System.out.println("\n✨ Pahlawan " + player.getNama() + " (" + player.getKelas() + ") telah dibuat!");
        player.displayStatus();
    }
    
    private void battle(Musuh enemy) {
        System.out.println(" " + enemy.getNama() + " (Level " + enemy.getLevel() + ") muncul!");
        System.out.println("HP: " + enemy.getHp() + " | ATK: " + enemy.getAttack() + " | DEF: " + enemy.getDefense());
        
        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- Turn Anda ---");
            playerTurn(enemy);
            
            if (enemy.isAlive()) {
                System.out.println("\n--- Turn Musuh ---");
                enemyTurn(enemy);
            }
            
            displayBattleStatus(enemy);
        }
        
        if (player.isAlive()) {
            System.out.println("\n Anda mengalahkan " + enemy.getNama() + "!");
            player.gainExp(enemy.getExpReward());
        }
    }
    
    private void bossBattle(Boss boss) {
        System.out.println(" " + boss.getNama() + " (Level " + boss.getLevel() + ") - BOSS muncul!");
        boss.displayStatus();
        
        while (player.isAlive() && boss.isAlive()) {
            System.out.println("\n--- Turn Anda ---");
            playerTurn(boss);
            
            if (boss.isAlive()) {
                System.out.println("\n--- Turn Boss ---");
                // Gunakan attackPlayer atau useSpecialSkill
                if (new Random().nextInt(100) < 40) {
                    boss.useSpecialSkill((Musuh) player);
                } else {
                    boss.attackPlayer(player);
                }
                
                // Display boss status jika enraged
                if (boss.isEnraged()) {
                    System.out.println("⚠️ BOSS DALAM MODE ENRAGED!");
                }
            }
            
            displayBossStatus(boss);
        }
        
        if (player.isAlive()) {
            System.out.println("\n🎉 Anda mengalahkan BOSS " + boss.getNama() + "!");
            player.gainExp(boss.getExpReward());
        }
    }
    
    private void playerTurn(Musuh enemy) {
        System.out.println("Pilih aksi:");
        System.out.println("1.  Serang");
        System.out.println("2.  Skill Khusus");
        System.out.println("3.  Heal (Level 3+)");
        System.out.println("4.  Status");
        System.out.print("Pilihan: ");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                player.attack(enemy);
                break;
            case 2:
                player.useSpecialSkill(enemy);
                break;
            case 3:
                if (player.getLevel() >= 3) {
                    int healAmount = 30 + (player.getLevel() * 5);
                    player.heal(healAmount);
                    System.out.println("💚 " + player.getNama() + " memulihkan " + healAmount + " HP!");
                } else {
                    System.out.println(" Heal tersedia mulai level 3!");
                    playerTurn(enemy); // retry turn
                }
                break;
            case 4:
                player.displayStatus();
                if (enemy instanceof Boss) {
                    ((Boss) enemy).displayStatus();
                }
                playerTurn(enemy); // retry turn
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                playerTurn(enemy); // retry turn
        }
    }
    
    private void enemyTurn(Musuh enemy) {
        if (new Random().nextInt(100) < 30) { // 30% chance for special skill
            enemy.useSpecialSkill((Musuh) player);
        } else {
            enemy.attackPlayer(player);
        }
    }
    
    private void displayBattleStatus(Musuh enemy) {
        System.out.println("\n " + player.getNama() + " HP: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println(" " + enemy.getNama() + " HP: " + enemy.getHp() + "/" + enemy.getMaxHp());
    }
    
    private void displayBossStatus(Boss boss) {
        System.out.println("\n " + player.getNama() + " HP: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println(" BOSS " + boss.getNama() + " HP: " + boss.getHp() + "/" + boss.getMaxHp() + 
                         (boss.isEnraged() ? " (ENRAGED!)" : ""));
        if (boss.getSkillCooldown() > 0) {
            System.out.println(" Boss Skill Cooldown: " + boss.getSkillCooldown() + " turns");
        }
    }
    
    private void restBetweenBattles() {
        System.out.println("\n Anda beristirahat sejenak...");
        int healAmount = player.getMaxHp() / 4;
        player.heal(healAmount);
        
        System.out.println(" HP dipulihkan sebesar " + healAmount);
        System.out.println("Tekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }
    
    private void victory() {
        System.out.println("\n ========== KEMENANGAN! ========== ");
        System.out.println("Selamat! Anda telah mengalahkan Dark Lord Malphas!");
        System.out.println("Dungeon telah dibersihkan dan kedamaian telah kembali!");
        System.out.println("" + player.getNama() + " akan dikenang sebagai pahlawan legendaris!");
        System.out.println("\n Status Akhir:");
        player.displayStatus();
    }
    
    private void gameOver() {
        System.out.println("\n ========== GAME OVER ========== ");
        System.out.println("Anda telah gugur dalam pertempuran...");
        System.out.println("Tapi jangan menyerah! Coba lagi dan jadilah lebih kuat!");
        System.out.println("\n Status Terakhir:");
        player.displayStatus();
    }
    
    public static void main(String[] args) {
        RPGGame game = new RPGGame();
        game.startGame();
    }
}
