package buildings.view;

import buildings.*;
import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFactory;
import buildings.dwelling.hotel.HotelBuilding;
import buildings.office.OfficeFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FirstForm {
    private JFrame frame;
    private Building building;
    JScrollPane jScrollPane;
    JPanel headPanel;
    JPanel[] jPanels;
    JButton[][] jButtons;
    JLabel[][] jLabelsBuildingInfo = new JLabel[3][2];
    JLabel[][] jLabelsFloorInfo = new JLabel[3][2];
    JLabel[][] jLabelsSpaceInfo = new JLabel[3][2];
    {
        jLabelsBuildingInfo[0][0] = new JLabel("Тип здания");
        jLabelsBuildingInfo[1][0] = new JLabel("Количество этажей");
        jLabelsBuildingInfo[2][0] = new JLabel("Общая площадь здания");
        jLabelsBuildingInfo[0][1] = new JLabel();
        jLabelsBuildingInfo[1][1] = new JLabel();
        jLabelsBuildingInfo[2][1] = new JLabel();

        jLabelsFloorInfo[0][0] = new JLabel("Номер этажа");
        jLabelsFloorInfo[1][0] = new JLabel("Количество помещений");
        jLabelsFloorInfo[2][0] = new JLabel("Общая площадь этажа");
        jLabelsFloorInfo[0][1] = new JLabel();
        jLabelsFloorInfo[1][1] = new JLabel();
        jLabelsFloorInfo[2][1] = new JLabel();

        jLabelsSpaceInfo[0][0] = new JLabel("Номер в здании");
        jLabelsSpaceInfo[1][0] = new JLabel("Количество комнат");
        jLabelsSpaceInfo[2][0] = new JLabel("Общая площадь");
        jLabelsSpaceInfo[0][1] = new JLabel();
        jLabelsSpaceInfo[1][1] = new JLabel();
        jLabelsSpaceInfo[2][1] = new JLabel();
    }
    int countPanel=0;

    public FirstForm() {
        this.frame = new JFrame("Buildings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        headPanel = new JPanel();
        headPanel.setLayout(new GridBagLayout());
        jScrollPane = new JScrollPane(headPanel);
        ScrollPaneLayout scrollPaneLayout = new ScrollPaneLayout();
        frame.setLayout(new GridBagLayout());
        frame.add(jScrollPane, new GridBagConstraints(0, 0, 1, 1, 1, 5,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));

        createMenuBar();

        frame.setVisible(true);
        frame.pack();
    }

    private Building inputBuilding(BuildingFactory buildingFactory){
        Building building = null;
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showOpenDialog(frame);
            Buildings.setBuildingFactory(buildingFactory);
            File file = jFileChooser.getSelectedFile();
            if (file.getName().contains(".txt")) {//todo .txt
                building = Buildings.inputBuilding(new FileInputStream(file));
            } else {
                building = Buildings.deserializeBuilding(new FileInputStream(file));
            }
            if(building instanceof Dwelling){
                if(building instanceof HotelBuilding){
                    jLabelsBuildingInfo[0][1].setText("Отель");
                }else jLabelsBuildingInfo[0][1].setText("Дом");
            }else jLabelsBuildingInfo[0][1].setText("Офис");
            jLabelsBuildingInfo[1][1].setText(String.valueOf(building.size()));
            jLabelsBuildingInfo[2][1].setText(String.valueOf(building.totalSpace()));
        }catch (Exception ex){
            JOptionPane.showMessageDialog(frame, new java.lang.String[]{ex.toString(), ex.getMessage(),
                            ex.getLocalizedMessage()},"ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return building;
    }

    private void createMenuBar( ){
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenuFile = new JMenu("File");
        JMenuItem jMenuItemOpenDwelling = new JMenuItem("Open dwelling…");
        JMenuItem jMenuItemOpenOffice = new JMenuItem("Open office building…");
        jMenuFile.add(jMenuItemOpenDwelling);
        jMenuFile.add(jMenuItemOpenOffice);
        jMenuBar.add(jMenuFile);
        frame.setJMenuBar(jMenuBar);

        jMenuItemOpenDwelling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                building = inputBuilding(new DwellingFactory());
                showBuilding();
            }
        });

        jMenuItemOpenOffice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                building = inputBuilding(new OfficeFactory());
                showBuilding();
            }
        });


        JMenu jMenuLookAndFeel = new JMenu("Look&Feel");
        if (System.getProperty("os.name").equals("Mac OS X")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            jMenuLookAndFeel.add(lookAndFeelInMenu(JCheckBoxMenuItem.class));
        } else {
            jMenuLookAndFeel.add(lookAndFeelInMenu(JRadioButton.class));
        }
        jMenuBar.add(jMenuLookAndFeel);

    }
    private AbstractButton lookAndFeelInMenu (Class<? extends AbstractButton> type) {
        try {
            UIManager.LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();
            ButtonGroup buttonGroup = new ButtonGroup();
            AbstractButton styleSelector;
            for (int i = 0; i < lafInfo.length; i++) {
                Constructor<? extends AbstractButton> ctor = type.getConstructor(String.class);
                styleSelector = ctor.newInstance(lafInfo[i].getName());
                final String s = lafInfo[i].getClassName();
                styleSelector.addActionListener((e) -> changeLookFeel(s));
                buttonGroup.add(styleSelector);
                return styleSelector;
            }
        } catch (NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void changeLookFeel(String s){
        try {
            UIManager.setLookAndFeel(s);
            SwingUtilities.updateComponentTreeUI(frame.getContentPane());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    private void showBuilding(){
        createJPanels();
        createButtons();
        createInfoPanel();

        frame.revalidate();
        frame.repaint();
    }

    private void createInfoPanel() {
        JPanel infoPanel = new JPanel();
        JPanel buildingInfo = new JPanel();
        JPanel floorInfo = new JPanel();
        JPanel spaceInfo = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        headPanel.add(infoPanel, new GridBagConstraints(0, countPanel, 1, 1, 1, 5,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
        infoPanel.add(buildingInfo, new GridBagConstraints(1, countPanel, 1, 1, 1, 5,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
        infoPanel.add(floorInfo, new GridBagConstraints(2, countPanel, 1, 1, 1, 5,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
        infoPanel.add(spaceInfo, new GridBagConstraints(3, countPanel++, 1, 1, 1, 5,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));



        createInfo(buildingInfo, jLabelsBuildingInfo);
        createInfo(floorInfo, jLabelsFloorInfo);
        createInfo(spaceInfo, jLabelsSpaceInfo);
    }

    private void createInfo(JPanel jPanel, JLabel[][] jLabels){
        for(int i=0;i<jLabels.length;i++) {
            for(int j=0;j<jLabels[i].length;j++) {
                jPanel.add(jLabels[i][j], new GridBagConstraints(j, i, 1, 1, 1, 5,
                        GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
//                jPanel.add(jLabels[i][j], new GridBagConstraints(j, i, 1, 1, 1, 5,
//                        GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
            }
        }
    }

    private void createJPanels(){
        jPanels = new JPanel[building.size()];
        for(int i=0;i<jPanels.length;i++){
            jPanels[i] = new JPanel();
            JScrollPane jScrollPane1 = new JScrollPane(jPanels[i]);
            headPanel.add(jScrollPane1, new GridBagConstraints(0, countPanel++, 1, 1, 1, 5,
                    GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
            final Integer integer = new Integer(i);
            jPanels[i].addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    jLabelsFloorInfo[0][1].setText(String.valueOf(integer));
                    jLabelsFloorInfo[1][1].setText(String.valueOf(building.getFloor(integer).size()));
                    jLabelsFloorInfo[2][1].setText(String.valueOf(building.getFloor(integer).totalSpace()));
                }

                @Override
                public void mousePressed(MouseEvent e) {
//                    System.out.println("2");

                }

                @Override
                public void mouseReleased(MouseEvent e) {
//                    System.out.println("3");

                }

                @Override
                public void mouseEntered(MouseEvent e) {
//                    System.out.println("4");

                }

                @Override
                public void mouseExited(MouseEvent e) {
//                    System.out.println("5");

                }
            });

        }
    }

    private void createButtons(){
        jButtons = new JButton[building.size()][];
        Floor floor;
        Space space;
        int count=0;
        for(int i=0;i<jButtons.length;i++){
            floor = building.getFloor(i);
            jButtons[i] = new JButton[floor.size()];
            for(int j=0;j<jButtons[i].length;j++){
                space = floor.get(j);
                final String[] strings = new String[]{
                        String.valueOf(count++),
                        String.valueOf(space.getSpace()),
                        String.valueOf(space.getNumberRooms())
                };
                jButtons[i][j]=new JButton("№ ".concat(strings[0]).concat(" Area: ").concat(strings[1])
                        .concat(" NumberRooms ").concat(strings[2]));
                jPanels[i].add(jButtons[i][j], new GridBagConstraints(j, i, 1, 1, 1, 5,
                        GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
                jButtons[i][j].addActionListener(e -> {
                    jLabelsSpaceInfo[0][1].setText(strings[0]);
                    jLabelsSpaceInfo[1][1].setText(strings[1]);
                    jLabelsSpaceInfo[2][1].setText(strings[2]);
                });

            }
        }
    }

//    private class MyListenerSpaces implements ActionListener{
//        private Space space;
//        private int number;
//
//        public MyListenerSpaces(Space space, int number) {
//            this.space = space;
//            this.number = number;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent event) {
//            jLabelsSpaceInfo[0][1].setText(String.valueOf(number));
//            jLabelsSpaceInfo[1][1].setText(String.valueOf(space.getNumberRooms()));
//            jLabelsSpaceInfo[2][1].setText(String.valueOf(space.getSpace()));
//        }
//    }

}
