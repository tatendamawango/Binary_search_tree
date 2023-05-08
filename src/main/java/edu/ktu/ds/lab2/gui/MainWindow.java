package edu.ktu.ds.lab2.gui;

//import com.sun.tools.javac.util.Convert;
import edu.ktu.ds.lab2.demo.Car;
import edu.ktu.ds.lab2.demo.CarsGenerator;
import edu.ktu.ds.lab2.utils.ParsableAvlSet;
import edu.ktu.ds.lab2.utils.ParsableBstSet;
import edu.ktu.ds.lab2.utils.ParsableSortedSet;
import edu.ktu.ds.lab2.utils.Set;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.*;

/**
 * Lab2 langas su JavaFX
 * <pre>
 *                     MainWindow (BorderPane)
 *  |-------------------------Top-------------------------|
 *  |                   MainWindowMenu                    |
 *  |------------------------Center-----------------------|
 *  |  |-----------------------------------------------|  |
 *  |  |                                               |  |
 *  |  |                                               |  |
 *  |  |                    taOutput                   |  |
 *  |  |                                               |  |
 *  |  |                                               |  |
 *  |  |                                               |  |
 *  |  |                                               |  |
 *  |  |                                               |  |
 *  |  |-----------------------------------------------|  |                                           |
 *  |------------------------Bottom-----------------------|
 *  |  |~~~~~~~~~~~~~~~~~~~paneBottom~~~~~~~~~~~~~~~~~~|  |
 *  |  |                                               |  |
 *  |  |  |-------------||------------||------------|  |  |
 *  |  |  | paneButtons || paneParam1 || paneParam2 |  |  |
 *  |  |  |             ||            ||            |  |  |
 *  |  |  |-------------||------------||------------|  |  |
 *  |  |                                               |  |
 *  |  |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|  |
 *  |-----------------------------------------------------|
 * </pre>
 *
 * @author darius.matulis@ktu.lt
 */
public class MainWindow extends BorderPane implements EventHandler<ActionEvent> {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("edu.ktu.ds.lab2.gui.messages");

    private static final int TF_WIDTH = 120;
    private static final int TF_WIDTH_SMALLER = 70;

    private static final double SPACING = 5.0;
    private static final Insets INSETS = new Insets(SPACING);
    private static final double SPACING_SMALLER = 2.0;
    private static final Insets INSETS_SMALLER = new Insets(SPACING_SMALLER);

    private final TextArea taOutput = new TextArea();
    private final GridPane paneBottom = new GridPane();
    private final GridPane paneParam2 = new GridPane();
    private final TextField tfDelimiter = new TextField();
    private final TextField tfInput = new TextField();
    private final ComboBox cmbTreeType = new ComboBox();

    private Panels paneParam1, paneButtons;
    private MainWindowMenu mainWindowMenu;
    private final Stage stage;

    private static ParsableSortedSet<Car> carsSet;
    private CarsGenerator carsGenerator = new CarsGenerator();

    private int sizeOfInitialSubSet, sizeOfGenSet, sizeOfLeftSubSet, balanceFactor;
    private double shuffleCoef;
    private final String[] errors;

    public MainWindow(Stage stage) {
        this.stage = stage;
        errors = new String[]{
                MESSAGES.getString("badSetSize"),
                MESSAGES.getString("badInitialData"),
                MESSAGES.getString("badSetSizes"),
                MESSAGES.getString("badShuffleCoef")
        };
        initComponents();
    }

    private void initComponents() {
        //======================================================================
        // Creates a result output VBox class object that
        // holds Label and TextArea class objects
        //======================================================================        
        VBox vboxTaOutput = new VBox();
        vboxTaOutput.setPadding(INSETS_SMALLER);
        VBox.setVgrow(taOutput, Priority.ALWAYS);
        vboxTaOutput.getChildren().addAll(new Label(MESSAGES.getString("border1")), taOutput);
        //======================================================================
        // A grid of buttons (blue) is formed. The Panels class is used.
        //======================================================================
        paneButtons = new Panels(
                new String[]{
                        MESSAGES.getString("button1"),
                        MESSAGES.getString("button2"),
                        MESSAGES.getString("button3"),
                        MESSAGES.getString("button5"),
                        MESSAGES.getString("button6"),
                        MESSAGES.getString("button7")},
                2, 4);
        disableButtons(true);
        //======================================================================
        // The first parameter table (green) is formed. The Panels class is used.
        //======================================================================
        paneParam1 = new Panels(
                new String[]{
                        MESSAGES.getString("lblParam11"),
                        MESSAGES.getString("lblParam12"),
                        MESSAGES.getString("lblParam13"),
                        MESSAGES.getString("lblParam14"),
                        MESSAGES.getString("lblParam15")},
                new String[]{
                        MESSAGES.getString("tfParam11"),
                        MESSAGES.getString("tfParam12"),
                        MESSAGES.getString("tfParam13"),
                        MESSAGES.getString("tfParam14"),
                        MESSAGES.getString("tfParam15")},
                TF_WIDTH_SMALLER);
        //======================================================================
        // A second parameter table (yellow) is formed.
        //======================================================================
        paneParam2.setAlignment(Pos.CENTER);
        paneParam2.setNodeOrientation(NodeOrientation.INHERIT);
        paneParam2.setVgap(SPACING);
        paneParam2.setHgap(SPACING);
        paneParam2.setPadding(INSETS);

        paneParam2.add(new Label(MESSAGES.getString("lblParam21")), 0, 0);
        paneParam2.add(new Label(MESSAGES.getString("lblParam22")), 0, 1);
        paneParam2.add(new Label(MESSAGES.getString("lblParam23")), 0, 2);

        cmbTreeType.setItems(FXCollections.observableArrayList(
                MESSAGES.getString("cmbTreeType1"),
                MESSAGES.getString("cmbTreeType2"),
                MESSAGES.getString("cmbTreeType3")
        ));
        cmbTreeType.setPrefWidth(TF_WIDTH);
        cmbTreeType.getSelectionModel().select(0);
        paneParam2.add(cmbTreeType, 1, 0);

        tfDelimiter.setPrefWidth(TF_WIDTH);
        tfDelimiter.setAlignment(Pos.CENTER);
        paneParam2.add(tfDelimiter, 1, 1);

        // Again, the first column, but the width is 2 cells
        tfInput.setEditable(false);
        tfInput.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        paneParam2.add(tfInput, 0, 3, 2, 1);
        //======================================================================
        // A common parameter panel (dark gray) is formed.
        //======================================================================
        paneBottom.setPadding(INSETS);
        paneBottom.setHgap(SPACING);
        paneBottom.setVgap(SPACING);
        paneBottom.add(paneButtons, 0, 0);
        paneBottom.add(paneParam1, 1, 0);
        paneBottom.add(paneParam2, 2, 0);
        paneBottom.alignmentProperty().bind(new SimpleObjectProperty<>(Pos.CENTER));

        mainWindowMenu = new MainWindowMenu() {
            @Override
            public void handle(ActionEvent ae) {
                Region region = (Region) taOutput.lookup(".content");
                region.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

                try {
                    Object source = ae.getSource();
                    if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(0))) {
                        fileChooseMenu();
                    } else if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(1))) {
                        KsGui.ounerr(taOutput, MESSAGES.getString("notImplemented"));
                    } else if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(3))) {
                        System.exit(0);
                    } else if (source.equals(mainWindowMenu.getMenus().get(1).getItems().get(0))) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle(MESSAGES.getString("menuItem21"));
                        alert.setHeaderText(MESSAGES.getString("author"));
                        alert.showAndWait();
                    }
                } catch (ValidationException e) {
                    KsGui.ounerr(taOutput, e.getMessage());
                } catch (Exception e) {
                    KsGui.ounerr(taOutput, MESSAGES.getString("systemError"));
                    e.printStackTrace(System.out);
                }
                KsGui.setFormatStartOfLine(false);
            }
        };

        //======================================================================
        // The Lab2 window is formed
        //======================================================================          
        // we place objects at the top, center and bottom ..
        setTop(mainWindowMenu);
        setCenter(vboxTaOutput);

        VBox vboxPaneBottom = new VBox();
        VBox.setVgrow(paneBottom, Priority.ALWAYS);
        vboxPaneBottom.getChildren().addAll(new Label(MESSAGES.getString("border2")), paneBottom);
        setBottom(vboxPaneBottom);
        appearance();

        paneButtons.getButtons().forEach(btn -> btn.setOnAction(this));
        cmbTreeType.setOnAction(this);
    }

    private void appearance() {
        paneButtons.setBackground(new Background(new BackgroundFill(Color.rgb(112, 162, 255)/* Pale blue */, CornerRadii.EMPTY, Insets.EMPTY)));
        paneParam1.setBackground(new Background(new BackgroundFill(Color.rgb(204, 255, 204)/* Light green */, CornerRadii.EMPTY, Insets.EMPTY)));
        paneParam1.getTfOfTable().get(2).setEditable(false);
        paneParam1.getTfOfTable().get(2).setStyle("-fx-text-fill: red");
        paneParam1.getTfOfTable().get(4).setEditable(true);
        paneParam2.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 153)/* Gelsva */, CornerRadii.EMPTY, Insets.EMPTY)));
        paneBottom.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        taOutput.setFont(Font.font("Monospaced", 12));
        taOutput.setStyle("-fx-text-fill: black;");
        taOutput.setEditable(false);
    }

    @Override
    public void handle(ActionEvent ae) {
        try {
            System.gc();
            System.gc();
            System.gc();
            Region region = (Region) taOutput.lookup(".content");
            region.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

            Object source = ae.getSource();
            if (source instanceof Button) {
                handleButtons(source);
            } else if (source instanceof ComboBox && source.equals(cmbTreeType)) {
                disableButtons(true);
            }
        } catch (ValidationException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                KsGui.ounerr(taOutput, errors[e.getCode()] + ": " + e.getMessage());
                if (e.getCode() == 2) {
                    paneParam1.getTfOfTable().get(0).setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                    paneParam1.getTfOfTable().get(1).setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            } else if (e.getCode() == 4) {
                KsGui.ounerr(taOutput, MESSAGES.getString("allSetIsPrinted"));
            } else {
                KsGui.ounerr(taOutput, e.getMessage());
            }
        } catch (UnsupportedOperationException e) {
            KsGui.ounerr(taOutput, e.getLocalizedMessage());
        } catch (Exception e) {
            KsGui.ounerr(taOutput, MESSAGES.getString("systemError"));
            e.printStackTrace(System.out);
        }
    }

    private void handleButtons(Object source) throws ValidationException {
        if (source.equals(paneButtons.getButtons().get(0))) {
            treeGeneration(null);
        } else if (source.equals(paneButtons.getButtons().get(1))) {
            treeIteration();
        } else if (source.equals(paneButtons.getButtons().get(2))) {
            treeAdd();
        } else if (source.equals(paneButtons.getButtons().get(3))) {
            treeRemove();
        } else if (source.equals(paneButtons.getButtons().get(4))) {
            treeBalance();
        } else if (source.equals(paneButtons.getButtons().get(5))) {
            runSpeedTest();
        }
    }

    private void runSpeedTest()
    {
        Object[] cars = carsSet.toArray();
        KsGui.setFormatStartOfLine(true);
        KsGui.ounerr(taOutput, "Object to remove: " + cars[cars.length-1].toString());
        long setRemoveTime = run(carsSet, (Car)cars[cars.length-1]);
        KsGui.ounerr(taOutput, "Removal Time: " + setRemoveTime);
        KsGui.setFormatStartOfLine(false);

    }
    static long run(Set<Car> carSet, Car remove)
    {
        long start = System.nanoTime();
        carSet.remove(remove);
        return System.nanoTime() - start;
    }

    private void treeGeneration(String filePath) throws ValidationException {
        // Scanning parameters
        readTreeParameters();
        // A set object is created, depending on the tree selection
        // cmbTreeType objects
        createTree();

        Car[] carsArray;
        // If no file is specified, generated
        if (filePath == null) {
            carsArray = carsGenerator.generateShuffle(sizeOfGenSet, sizeOfInitialSubSet, shuffleCoef);
            paneParam1.getTfOfTable().get(2).setText(String.valueOf(sizeOfLeftSubSet));
        } else { // Reading from file
            carsSet.load(filePath);
            carsArray = new Car[carsSet.size()];
            int i = 0;
            for (Object o : carsSet.toArray()) {
                carsArray[i++] = (Car) o;
            }
            // When reading from a file, it is shuffled using the standard Collections.shuffle method.
            Collections.shuffle(Arrays.asList(carsArray), new Random());
        }

        // The elements of the shuffled array are listed in a set
        carsSet.clear();
        Arrays.stream(carsArray).forEach(carsSet::add);

        // Outputs results
        // It is set not to count the number of output rows at the beginning of a line
        KsGui.setFormatStartOfLine(true);
        KsGui.oun(taOutput, carsSet.toVisualizedString(tfDelimiter.getText()),
                MESSAGES.getString("setInTree"));
        // Set to count the number of output lines at the beginning of the line
        KsGui.setFormatStartOfLine(false);
        disableButtons(false);
    }

    private void treeAdd() throws ValidationException {
        KsGui.setFormatStartOfLine(true);
        Car car = carsGenerator.takeCar();
        carsSet.add(car);
        paneParam1.getTfOfTable().get(2).setText(String.valueOf(--sizeOfLeftSubSet));
        KsGui.oun(taOutput, car, MESSAGES.getString("setAdd"));
        KsGui.oun(taOutput, carsSet.toVisualizedString(tfDelimiter.getText()));
        KsGui.setFormatStartOfLine(false);
    }

    private void treeRemove() {
        KsGui.setFormatStartOfLine(true);
        if (carsSet.isEmpty()) {
            KsGui.ounerr(taOutput, MESSAGES.getString("setIsEmpty"));
            KsGui.oun(taOutput, carsSet.toVisualizedString(tfDelimiter.getText()));
        } else {
            int nr = new Random().nextInt(carsSet.size());
            Car car = (Car) carsSet.toArray()[nr];
            carsSet.remove(car);
            KsGui.oun(taOutput, car, MESSAGES.getString("setRemoval"));
            KsGui.oun(taOutput, carsSet.toVisualizedString(tfDelimiter.getText()));
        }
        KsGui.setFormatStartOfLine(false);
    }

    private void treeBalance() {
        KsGui.setFormatStartOfLine(true);
        if (carsSet.isEmpty()) {
            KsGui.ounerr(taOutput, MESSAGES.getString("setIsEmpty"));
            KsGui.oun(taOutput, carsSet.toVisualizedString(tfDelimiter.getText()));
        } else {
            int k = 0;
            balanceFactor = Integer.parseInt(paneParam1.getParametersOfTable().get(4).replace("-", "x"));
            paneParam1.getTfOfTable().get(4).setText(String.valueOf(balanceFactor));
            if (balanceFactor > -1)
            {
            boolean b = carsSet.getBalance(balanceFactor);
            KsGui.ounerr(taOutput," " + b);}
            else
            { KsGui.ounerr(taOutput, "k is not valid");}

        }
        KsGui.setFormatStartOfLine(false);
    }

    private void treeIteration() {
        KsGui.setFormatStartOfLine(true);
        if (carsSet.isEmpty()) {
            KsGui.ounerr(taOutput, MESSAGES.getString("setIsEmpty"));
        } else {
            KsGui.oun(taOutput, carsSet, MESSAGES.getString("setIterator"));
        }
        KsGui.setFormatStartOfLine(false);
    }

    private void readTreeParameters() throws ValidationException {
        // A little cosmetics ..
        for (int i = 0; i < 2; i++) {
            paneParam1.getTfOfTable().get(i).setStyle("-fx-control-inner-background: white; ");
            paneParam1.getTfOfTable().get(i).applyCss();
        }
        // Parameter values are read. If converting from String
        // If an converting from String error occurs, a NumberFormatException situation is generated.
        // MyException is used to distinguish where JTextfield error occurred

        int i = 0;
        try {
            // Replace to cause a situation in
            // to a negative number
            sizeOfGenSet = Integer.parseInt(paneParam1.getParametersOfTable().get(i).replace("-", "x"));
            sizeOfInitialSubSet = Integer.parseInt(paneParam1.getParametersOfTable().get(++i).replace("-", "x"));
            sizeOfLeftSubSet = sizeOfGenSet - sizeOfInitialSubSet;
            ++i;
            shuffleCoef = Double.parseDouble(paneParam1.getParametersOfTable().get(++i).replace("-", "x"));
        } catch (NumberFormatException e) {
            // It is also possible to catch an exception and throw again
            throw new ValidationException(paneParam1.getParametersOfTable().get(i), e, i);
        }
    }

    private void createTree() throws ValidationException {
        switch (cmbTreeType.getSelectionModel().getSelectedIndex()) {
            case 0:
                carsSet = new ParsableBstSet<>(Car::new);
                break;
            case 1:
                carsSet = new ParsableAvlSet<>(Car::new);
                break;
            default:
                disableButtons(true);
                throw new ValidationException(MESSAGES.getString("notImplemented"));
        }
    }

    private void disableButtons(boolean disable) {
        for (int i : new int[]{1, 2, 3, 4, 5}) {
            if (i < paneButtons.getButtons().size() && paneButtons.getButtons().get(i) != null) {
                paneButtons.getButtons().get(i).setDisable(disable);
            }
        }
    }

    private void fileChooseMenu() throws ValidationException {
        FileChooser fc = new FileChooser();
        // Additional with our filters
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        fc.setTitle((MESSAGES.getString("menuItem11")));
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            treeGeneration(file.getAbsolutePath());
        }
    }

    public static void createAndShowGui(Stage stage) {
        Locale.setDefault(Locale.US); // We unify number formats
        MainWindow window = new MainWindow(stage);
        stage.setScene(new Scene(window, 1100, 650));
        stage.setTitle(MESSAGES.getString("title"));
        stage.getIcons().add(new Image("file:" + MESSAGES.getString("icon")));
        stage.show();
    }
}
