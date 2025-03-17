package ui;
//
import domain.enchantments.*;
import domain.halls.*;
import domain.monsters.FighterMonster;
import domain.monsters.ArcherMonster;
import domain.Player;
import domain.monsters.Monster;
import domain.monsters.WizardMonster;
import domain.strategy.WizardStrategy;
import persistence.SaveGameAdapter;
import persistence.SerializableSaveGameAdapter;
import physics.Movements;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Timer;

public class GameMode {
    private Player player;
    private Hall hall;
    private HallOfAir hallOfAir;
    private HallOfEarth hallOfEarth;
    private HallOfWater hallOfWater;
    private HallOfFire hallOfFire;
    private JLabel playerLabel;
    private JFrame frame;
    private boolean isFlipped = false;
    private Set<Integer> pressedKeys = new HashSet<>();
    private Timer movementTimer;
    private Timer timeUpdateTimer;
    private WizardMonster wizardMonster;
    private SaveGameAdapter saveGameAdapter;
    private GameModeImageFactory imageFactory;
    private JPanel pauseOverlay;
    private JLabel movingLuringGemLabel;


    private JLabel runeLabel;

    public Rectangle hintRectangle; // Highlighted area for the rune
    public JPanel drawingPanel;
    private JLabel selectedLabel;
    private List<Monster> monsters;
    public transient  List<Timer> timers = new ArrayList<>();
    private boolean isPaused = false;

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    private FeaturePanel featurePanel;

    private JLabel luringGemLabel; // Label for the Luring Gem
    private JLabel revealLabel; // Reveal JLabel
    private JLabel cloakLabel; // Label for the Cloak of Protection
    private JLabel extraTimeLabel;
    private JLabel extraLifeLabel;

    public JLabel getExtraLifeLabel() {
        return extraLifeLabel;
    }
    public JLabel getExtraTimeLabel() {
        return extraTimeLabel;
    }

    public void setExtraLifeLabel(JLabel extraLifeLabel) {
        this.extraLifeLabel = extraLifeLabel;
    }
    public void setExtraTimeLabel(JLabel extraTimeLabel) {
        this.extraTimeLabel = extraTimeLabel;
    }


    private LuringGem luringGem; // Initial Luring Gem enchantment
    private Reveal reveal; // Reveal enchantment
    private CloakOfProtection cloakOfProtection; // Cloak of Protection enchantment
    private ExtraLife extraLife;
    private ExtraTime extraTime;

    public GameMode(Player player, HallOfEarth hallOfEarth, HallOfAir hallOfAir, HallOfWater hallOfWater, HallOfFire hallOfFire) {
        this.player = player;
        this.hallOfAir = hallOfAir;
        this.hallOfEarth = hallOfEarth;
        this.hallOfWater = hallOfWater;
        this.hallOfFire = hallOfFire;
        this.monsters = new ArrayList<>();
        this.saveGameAdapter = new SerializableSaveGameAdapter();
        this.imageFactory = new GameModeImageFactory();

        if(player.getLevel()==1){
            hall = hallOfEarth;
        }
        else if (player.getLevel()==2){
            hall = hallOfAir;
        }
        else if (player.getLevel()==3){
            hall = hallOfWater;
        }
        else if (player.getLevel()==4){
            hall = hallOfFire;
        }
        player.setRemainingTime(hall.getItemCount()*10);
        player.setInitialTime(hall.getItemCount()*10);
        Movements.initializeImages(imageFactory);
        createBackground();
        addDrawingPanel();
        this.pauseOverlay = GameModeOverlays.showPauseOverlay(this);
        runeLabel = imageFactory.addRuneImage(this);
        playerLabel=imageFactory.addPlayerImage(this);
        revealLabel  = imageFactory.addRevealImage(this);
        cloakLabel=imageFactory.addCloakImage(this);
        luringGemLabel = imageFactory.addLuringGemImage(this);
        extraLifeLabel = imageFactory.addExtraLifeImage(this);
        extraTimeLabel = imageFactory.addExtraTimeImage(this);
        addKeyListeners();
        startMovementTimer();
        timers.add(Movements.startImageSwitchTimer());

        startRemainingTimeTimer();
        startMonsterGenerationTimer(); // Start the monster generation timer
        reveal = new Reveal();
        cloakOfProtection = new CloakOfProtection();
        this.extraLife = new ExtraLife();
        this.luringGem = new LuringGem();
        hintRectangle = null;
        timers.add(EnchantmentCreator.generateRandomEnchantment(this,imageFactory)); // Generate random enchantments
        frame.setVisible(true);
        frame.repaint();
        drawHall(hall);

    }

    private void createBackground() {
        frame = new JFrame("Game Mode");
        frame.setResizable(false);
        frame.setBounds(100, 100, 1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.getContentPane().setLayout(null);
        System.out.println(player.getLevel());
        BufferedImage background = BackgroundCreator.createBackground();
        ImageIcon backgroundIcon = new ImageIcon(background);
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1080, 720);
        frame.getContentPane().add(backgroundLabel);

        featurePanel = GameModeFeatures.addFeaturePanel(frame, this.player, this); // Add the feature panel using the new method
        frame.getContentPane().setComponentZOrder(backgroundLabel, 1);
    }

    private void drawHall(Hall hall){

        // Add the hall panel to the frame
        JPanel hallPanel = hall.getUIController().getHallPanel();
        hallPanel.setBounds(0, 0, 700, 640);
        hallPanel.setLayout(null); // Set layout to null
        removeMouseListeners(hallPanel); // Remove existing MouseListeners
        frame.getContentPane().add(hallPanel);
        frame.getContentPane().setComponentZOrder(hallPanel, 3); // Ensure hallPanel is on top

        for (JLabel label : hall.getUIController().getSavedLabels()) {
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (isPlayerNextToObject(label)) {
                        if (label.equals(selectedLabel)) {
                            checkRuneFound();   // Check if the player found the rune
                        } else {
                            ResourceLoader.playSound("assets/sounds/not_here.wav");
                        }
                    } else {
                    }
                }
            });
        }

        // Place the rune in one of the saved labels
        if (!hall.getUIController().getSavedLabels().isEmpty()) {
            selectedLabel = hall.getUIController().getSavedLabels().get((int) (Math.random() * hall.getUIController().getSavedLabels().size()));
            runeLabel.setLocation(selectedLabel.getX(), selectedLabel.getY());
            frame.getContentPane().add(runeLabel);
            frame.getContentPane().setComponentZOrder(runeLabel, 1);
            frame.revalidate();
            frame.repaint();
        }
    }

    private void removeMouseListeners(JPanel panel) {
        for (MouseListener listener : panel.getMouseListeners()) {
            panel.removeMouseListener(listener);
        }
    }
    public void teleportRune() {
        /**
         * Teleports the rune to a random saved label location within the current hall.
         *
         * REQUIRES:
         * - The `hall` object and its `UIController` must be properly initialized.
         * - `hall.getUIController().getSavedLabels()` should contain at least one label.
         * - `runeLabel` must be initialized and added to the game frame.
         *
         * MODIFIES:
         * - The location of the `runeLabel`.
         * - The `selectedLabel` variable.
         *
         * EFFECTS:
         * - Moves the `runeLabel` to a new random position among the `savedLabels`.
         * - Prints the new position of the `runeLabel` to the console.
         * - Updates the game frame to reflect the rune's new position.
         */
        List<JLabel> savedLabels = hall.getUIController().getSavedLabels();
        if (!savedLabels.isEmpty()) {
            JLabel nextLabel = savedLabels.get((int) (Math.random() * savedLabels.size()));
            selectedLabel = nextLabel;
            runeLabel.setLocation(nextLabel.getX(), nextLabel.getY());
            System.out.println("Rune teleported to: (" + nextLabel.getX() + ", " + nextLabel.getY() + ")");
            frame.revalidate();
            frame.repaint();
            ResourceLoader.playSound("assets/sounds/teleport_sound.wav");
        }
    }

    public void updateFeatures() {
        featurePanel = GameModeFeatures.refreshFeaturePanel(featurePanel, this.player,this);
    }

    public void moveLuringGem(String direction, JLabel usingluringGemLabel) {
        int stepSize = 5; // Adjust the step size as needed
        Timer luringGemTimer = new Timer(16, new ActionListener() {
            int currentX = usingluringGemLabel.getX();
            int currentY = usingluringGemLabel.getY();

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (direction.toUpperCase()) {
                    case "A": // Left
                        currentX -= stepSize;
                        break;
                    case "D": // Right
                        currentX += stepSize;
                        break;
                    case "W": // Up
                        currentY -= stepSize;
                        break;
                    case "S": // Down
                        currentY += stepSize;
                        break;
                }

                usingluringGemLabel.setLocation(currentX, currentY);
                usingluringGemLabel.setVisible(true); // Ensure the luring gem is visible while moving
                movingLuringGemLabel = usingluringGemLabel;

                // Check for out-of-bounds
                if (isOutOfBounds(currentX, currentY)) {
                    ((Timer) e.getSource()).stop();
                    usingluringGemLabel.setVisible(false);
                    System.out.println("Luring Gem went out of bounds.");
                    luringGem.setIsActive(false);
                    movingLuringGemLabel = null;
                }
            }
        });

        luringGemTimer.start();
        timers.add(luringGemTimer);
    }


    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 || x > frame.getWidth() || y > frame.getHeight();
    }

    private void addKeyListeners() {
        frame.addKeyListener(new KeyAdapter() {
            private boolean isBPressed = false;

            @Override
            public void keyPressed(KeyEvent e) {
                if(isPaused){
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_B) {
                    isBPressed = true;
                } else if (isBPressed) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_W:
                            EnchantmentFeatures.useLuringGem("W",GameMode.this);
                            break;
                        case KeyEvent.VK_A:
                            EnchantmentFeatures.useLuringGem("A",GameMode.this);
                            break;
                        case KeyEvent.VK_S:
                            EnchantmentFeatures.useLuringGem("S",GameMode.this);
                            break;
                        case KeyEvent.VK_D:
                            EnchantmentFeatures.useLuringGem("D",GameMode.this);
                            break;
                    }
                    isBPressed = false; // Reset the flag after using the luring gem
                }

                if (e.getKeyCode() == KeyEvent.VK_R) {
                    EnchantmentFeatures.revealRuneHint(GameMode.this,reveal,runeLabel,hintRectangle,drawingPanel);
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    EnchantmentFeatures.activateCloak(GameMode.this,cloakOfProtection,archerAttacks);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                }
                pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public Player getPlayer() {
        return player;
    }

    private void startMovementTimer() {
        movementTimer = new Timer(16, e -> {
            java.util.List<Rectangle> objectBounds = new ArrayList<>();

            if(player.getHealth()<1){
                terminateTheGame();
                GameEndingScreen gameEndingScreen = new GameEndingScreen(player,false);
                gameEndingScreen.showGameOverScreen(this.getFrame());
                return;
            }

            for (Monster monster : monsters) {
                objectBounds.add(monster.getMonsterLabel().getBounds());
            }

            // Add bounds of hall objects
            for (Component component : hall.getUIController().getHallPanel().getComponents()) {
                if (component instanceof JLabel) {
                    objectBounds.add(component.getBounds());
                }
            }
            Movements.playerMovement(pressedKeys, GameMode.this, objectBounds);
            for (Monster monster : monsters) {
                if (monster instanceof FighterMonster) {
                    if(movingLuringGemLabel!=null){
                        ((FighterMonster) monster).moveTowardsLuringGem(this);
                    }
                    else {
                        ((FighterMonster) monster).moveRandomly(playerLabel.getBounds(), hall.getUIController().getSavedLabels(), monsters);
                    }
                }
            }
            if (WizardMonster.wizardCount > 0) {
                WizardStrategy wizardStrategy = wizardMonster.getStrategy();
                wizardMonster.determineWizardStrategy();
                if(wizardStrategy.getWizardType()!=wizardMonster.getStrategy().getWizardType()){
                    wizardMonster.getStrategy().execute(this, wizardMonster);
                }


            }
            checkRevealCollision();
            checkCloakCollision();
            checkLuringGemCollision(luringGem);
            checkExtraLifeCollision();
            checkExtraTimeCollision();

            // Ensure player label is on top
            frame.getContentPane().setComponentZOrder(playerLabel, 0);
            frame.revalidate();
            frame.repaint();
            // Check if the player is next to the open door
            if (hall.isDoorOpen() && isPlayerNextToDoor()) {
                switchToNextHall();
            }
        });
        movementTimer.start();
        timers.add(movementTimer);
    }

    private void startRemainingTimeTimer() {
        timeUpdateTimer = new Timer(1000, e -> {
            if(isPaused){
                return;
            }
            if (player.getRemainingTime() > 0) {
                player.decreaseRemainingTime();
                GameModeFeatures.getRemainingTimeLabel().setText(player.getRemainingTime() + " seconds");
                GameModeFeatures.getRemainingTimeLabel().repaint();
            } else {
                timeUpdateTimer.stop();
                movementTimer.stop();
                JOptionPane.showMessageDialog(frame, "Your time is up!", "Time's Up", JOptionPane.WARNING_MESSAGE);
                switchToBuildMode();
            }
        });
        timeUpdateTimer.start();
        timers.add(timeUpdateTimer);
    }
    private void checkCloakCollision() {
        if (cloakLabel != null && playerLabel.getBounds().intersects(cloakLabel.getBounds())) {
            EnchantmentFeatures.collectCloak(this);
        }
    }

    private void checkRevealCollision() {
        if (revealLabel != null && playerLabel.getBounds().intersects(revealLabel.getBounds())) {
            EnchantmentFeatures.collectRevealEnchantment(this,reveal);
        }
    }

    private void checkLuringGemCollision(LuringGem luringGem) {
        if (luringGemLabel != null && playerLabel.getBounds().intersects(luringGemLabel.getBounds()) && luringGem.isCollectable()) {
            System.out.println(luringGem.isCollectable());
            EnchantmentFeatures.collectLuringGem(this);
        }
    }

    private void checkExtraLifeCollision() {
        if (extraLifeLabel != null && playerLabel.getBounds().intersects(extraLifeLabel.getBounds())) {
                EnchantmentFeatures.collectExtraLife(this);
        }
    }

    private void checkExtraTimeCollision() {
        if (extraTimeLabel != null && playerLabel.getBounds().intersects(extraTimeLabel.getBounds())) {
            EnchantmentFeatures.collectExtraTime(this);
        }
    }

    private void addDrawingPanel() {
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw the hint rectangle if active
                if (hintRectangle != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(new Color(204, 255, 204, 128)); // Semi-transparent pale pink-green fill
                    g2d.fillRect(hintRectangle.x, hintRectangle.y, hintRectangle.width, hintRectangle.height);
                    g2d.setColor(new Color(204, 255, 204)); // Pale pink-green outline
                    g2d.drawRect(hintRectangle.x, hintRectangle.y, hintRectangle.width, hintRectangle.height);
                }
            }
        };

        // Set the drawing panel bounds to match the game frame
        drawingPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        drawingPanel.setOpaque(false); // Transparent to show game elements beneath
        frame.getContentPane().add(drawingPanel);
        frame.getContentPane().setComponentZOrder(drawingPanel, 0); // Ensure it is above other elements
        frame.revalidate();
        frame.repaint();
    }

    public JLabel getPlayerLabel() {
        return playerLabel;
    }

    public void switchToBuildMode(){
        frame.dispose();
        List<Hall> otherHalls = new ArrayList<>();
        otherHalls.add(hallOfEarth);
        otherHalls.add(hallOfAir);
        otherHalls.add(hallOfWater);
        otherHalls.add(hallOfFire);
        for (Timer timer : timers) {
            timer.stop();
        }
        if(wizardMonster!=null){
            wizardMonster.getStrategy().stopTimer();
        }
        for(ArcherAttack archerAttack : archerAttacks.values()){
            archerAttack.stopTimers();
        }
        FighterMonster.fighterCount = 0;
        ArcherMonster.archerCount = 0;
        WizardMonster.wizardCount = 0;
        monsters.clear();
        player.increaseLevel();
        frame.dispose();
        new BuildMode(player,hall,otherHalls);

    }

    private boolean isPlayerNextToObject(JLabel objectLabel) {
        int playerX = player.getX();
        int playerY = player.getY();
        int objectX = objectLabel.getX();
        int objectY = objectLabel.getY();
        int distance = 50; // Define the maximum distance to consider the player "next to" the object

        return Math.abs(playerX - objectX) <= distance && Math.abs(playerY - objectY) <= distance;
    }

    private Map<ArcherMonster, ArcherAttack> archerAttacks = new HashMap<>();

    private void startMonsterGenerationTimer() {
        Timer monsterGenerationTimer = new Timer(4000, e -> {
            Monster monster = MonsterCreator.randomMonsterGenerate(frame,player);
            MonsterCreator.placeRandomMonster(this,monster);
        });
        monsterGenerationTimer.start();
        timers.add(monsterGenerationTimer);
    }
    public void archerMechanism(ArcherMonster monster){
        ArcherAttack archerAttack = new ArcherAttack(monster,player,frame,playerLabel,this);
        archerAttacks.put(monster, archerAttack); // Store the ArcherAttack instance
        timers.add(archerAttack.getAttackTimer());
    }
    public void wizardMechanism(WizardMonster monster){
        monster.getStrategy().execute(this, monster);
    }
    public void fighterMechanism(FighterMonster fighter) {
        FighterAttack fighterAttack = new FighterAttack(this,fighter);
        timers.add(fighterAttack.getAttackTimer());
    }

    public void removeMonster(Monster monster) {
        frame.getContentPane().remove(monster.getMonsterLabel());
        frame.revalidate();
        frame.repaint();
        monsters.remove(monster);
    }

    private void checkRuneFound() {
        if (isPlayerNextToObject(selectedLabel)) {
            runeLabel.setVisible(true);
            hall.getUIController().openDoor(); // Call the method to change the door image
            frame.revalidate();
            frame.repaint();
            hall.getUIController().getHallPanel().revalidate();
            hall.getUIController().getHallPanel().repaint();
        }
    }

    private boolean isPlayerNextToDoor() {
        int playerX = player.getX();
        int playerY = player.getY();
        int doorX = hall.getUIController().getDoorPosition();
        int doorY = hall.getUIController().getHeight() - 32; // Assuming the door is at the bottom of the hall
        int distance = 15; // Define the maximum distance to consider the player "next to" the door

        return Math.abs(playerX - doorX) <= distance && Math.abs(playerY - doorY) <= distance;
    }

    public void switchToNextHall() {
        if(player.getLevel()==4){
            terminateTheGame();
            GameEndingScreen gameEndingScreen = new GameEndingScreen(player,true);
            gameEndingScreen.showGameWinScreen(this.getFrame());
            return;
        }
        for(ArcherAttack archerAttack : archerAttacks.values()){
            archerAttack.stopTimers();
        }
        for (Timer timer : timers) {
            timer.stop();
        }
        if(wizardMonster!=null){
            wizardMonster.getStrategy().stopTimer();
        }
        JOptionPane.showMessageDialog(frame, "Congrats! You will pass to the next hall.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
        FighterMonster.fighterCount = 0;
        ArcherMonster.archerCount = 0;
        WizardMonster.wizardCount = 0;
        monsters.clear();
        player.increaseLevel();
        frame.dispose();
        new GameMode(player, hallOfEarth, hallOfAir, hallOfWater, hallOfFire);
    }

    public void pauseGame() {
        if (!isPaused) {
            for (Timer timer : timers) {
                timer.stop();
            }
            for(ArcherAttack archerAttack : archerAttacks.values()){
                archerAttack.stopTimers();
            }
            if(wizardMonster!=null){
                wizardMonster.getStrategy().stopTimer();
            }
            pauseOverlay.setVisible(true);
            isPaused = true;
        }
    }

    public void resumeGame() {
        if (isPaused) {
            for (Timer timer : timers) {
                timer.start();
            }
            for(ArcherAttack archerAttack : archerAttacks.values()){
                archerAttack.continueTimers();
            }
            if(wizardMonster!=null){
                wizardMonster.getStrategy().resumeTimer();
            }
            pauseOverlay.setVisible(false);
            isPaused = false;
        }
    }

    public boolean getIsPaused(){
        return isPaused;
    }
    public void saveGame(String fileName) {
        SaveGameAdapter adapter = new SerializableSaveGameAdapter();
        SaveAndLoad.saveGame(adapter,fileName,player, List.of(hallOfAir, hallOfFire, hallOfWater, hallOfEarth), monsters);
    }

    public void loadGame(String fileName) {
        Object[] gameState = SaveAndLoad.loadGame(saveGameAdapter,fileName, frame);
        if (gameState != null) {
            this.player = (Player) gameState[0];
            List<Hall> loadedHalls = (List<Hall>) gameState[1];
            this.hallOfAir = (HallOfAir) loadedHalls.get(0);
            this.hallOfFire = (HallOfFire) loadedHalls.get(1);
            this.hallOfWater = (HallOfWater) loadedHalls.get(2);
            this.hallOfEarth = (HallOfEarth) loadedHalls.get(3);
            if(player.getLevel()==1){
                hall = hallOfEarth;
            }
            else if (player.getLevel()==2){
                hall = hallOfAir;
            }
            else if (player.getLevel()==3){
                hall = hallOfWater;
            }
            else if (player.getLevel()==4){
                hall = hallOfFire;
            }
            drawHall(hall);
            this.monsters = (List<Monster>) gameState[2];
            SaveAndLoad.loadMonsters(monsters,this,frame);
            SaveAndLoad.refreshFeaturePanel(this);
        }
    }

    public Hall getHall() {
        return hall;
    }
    public HallOfAir getHallOfAir() {
        return hallOfAir;
    }
    public HallOfEarth getHallOfEarth() {
        return hallOfEarth;
    }
    public HallOfFire getHallOfFire() {
        return hallOfFire;
    }
    public HallOfWater getHallOfWater() {
        return hallOfWater;
    }

    public JFrame getFrame() {
        return frame;
    }


    public void setFeaturePanel(FeaturePanel featurePanel) {
        this.featurePanel = featurePanel;
    }
    public FeaturePanel getFeaturePanel() {
        return featurePanel;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setPlayerLabel(JLabel playerLabel) {
        this.playerLabel = playerLabel;
    }

    public void terminateTheGame(){
        //Stop all active timers
        frame.getContentPane().removeAll();
        for (Timer timer : timers) {
            timer.stop();
        }
        for(ArcherAttack archerAttack : archerAttacks.values()){
            archerAttack.stopTimers();
        }
        if(wizardMonster!=null){
            wizardMonster.getStrategy().stopTimer();
        }
        FighterMonster.fighterCount = 0;
        ArcherMonster.archerCount = 0;
        WizardMonster.wizardCount = 0;
        player.resetPlayer();
    }
    public JLabel getRevealLabel() {
        return revealLabel;
    }
    public void setRevealLabel(JLabel revealLabel) {
        this.revealLabel = revealLabel;
    }
    public JLabel getCloakLabel() {
        return cloakLabel;
    }
    public void setCloakLabel(JLabel cloakLabel) {
        this.cloakLabel = cloakLabel;
    }
    public JLabel getLuringGemLabel() {
        return luringGemLabel;
    }
    public void setLuringGemLabel(JLabel luringGemLabel) {
        this.luringGemLabel = luringGemLabel;
    }
    public List<Timer> getTimers(){
        return timers;
    }
    public Rectangle getHintRectangle() {
        return hintRectangle;
    }
    public void setHintRectangle(Rectangle hintRectangle) {
        this.hintRectangle = hintRectangle;
    }
    public JLabel getRuneLabel(){
        return runeLabel;
    }
    public JPanel getDrawingPanel(){
        return drawingPanel;
    }

    public void setWizardMonster(WizardMonster randomMonster) {
        this.wizardMonster = randomMonster;
    }

    public JLabel getMovingLuringGemLabel() {
        return movingLuringGemLabel;
    }

    public List<JLabel> getSavedLabels() {
        return hall.getUIController().getSavedLabels();
    }
}