import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CategoryManagementApp extends Application {
    private TableView<Category> tableView;
    private TextField nameField;
    private TextField searchField;
    private CategoryDAO categoryDAO = new CategoryDAO();
    private ObservableList<Category> categoryList;

    @Override
    public void start(Stage primaryStage) {
        tableView = new TableView<>();
        nameField = new TextField();
        searchField = new TextField();

        TableColumn<Category, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        
        TableColumn<Category, String> nameColumn = new TableColumn<>("Category Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        
        tableView.getColumns().addAll(idColumn, nameColumn);
        loadCategories();

        Button addButton = new Button("Add Category");
        addButton.setOnAction(e -> {
            String categoryName = nameField.getText();
            categoryDAO.addCategory(categoryName);
            loadCategories();
            nameField.clear();
        });

        Button updateButton = new Button("Update Category");
        updateButton.setOnAction(e -> {
            Category selectedCategory = tableView.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                selectedCategory.setName(nameField.getText());
                categoryDAO.updateCategory(selectedCategory.getId(), selectedCategory.getName());
                loadCategories();
            }
        });

        Button deleteButton = new Button("Delete Category");
        deleteButton.setOnAction(e -> {
            Category selectedCategory = tableView.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                categoryDAO.deleteCategory(selectedCategory.getId());
                loadCategories();
            }
        });

        Button searchButton = new Button("Search Category");
        searchButton.setOnAction(e -> {
            String name = searchField.getText();
            categoryList.setAll(categoryDAO.searchCategories(name));
        });

        HBox inputBox = new HBox(10, new Label("Category Name:"), nameField, addButton, updateButton, deleteButton);
        HBox searchBox = new HBox(10, new Label("Search:"), searchField, searchButton);
        VBox vbox = new VBox(10, inputBox, searchBox, tableView);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Category Management");
        primaryStage.show();
    }

    private void loadCategories() {
        categoryList = FXCollections.observableArrayList(categoryDAO.getAllCategories());
        tableView.setItems(categoryList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

