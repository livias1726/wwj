package logic.presentation.control;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.bean.OfferBean;
import logic.exceptions.DatabaseFailureException;
import logic.presentation.GraphicHandler;
import logic.presentation.Scenes;

public class OffersInfoGraphic implements Initializable {
	
	@FXML
	private AnchorPane offersPane;
	
	@FXML
    private RadioButton allRadio;

    @FXML
    private RadioButton actRadio;

    @FXML
    private RadioButton expRadio;
    
    @FXML
    private ToggleGroup views;

    @FXML
    private TableView<OfferBean> table;
    
    @FXML
    private TableColumn<OfferBean, Integer> numCol;

    @FXML
    private TableColumn<OfferBean, String> posCol;

    @FXML
    private TableColumn<OfferBean, LocalDate> upCol;

    @FXML
    private TableColumn<OfferBean, LocalDate> exCol;

    @FXML
    private TableColumn<OfferBean, Integer> candCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		OfferBean bean = new OfferBean();
		List <OfferBean> list = null;
		try {
			list = bean.getPublishedOffers();
			
			numCol.setCellValueFactory(new PropertyValueFactory<>("id"));
			posCol.setCellValueFactory(new PropertyValueFactory<>("position"));
			upCol.setCellValueFactory(new PropertyValueFactory<>("upload"));
			exCol.setCellValueFactory(new PropertyValueFactory<>("expiration"));
			candCol.setCellValueFactory(new PropertyValueFactory<>("candidates"));
			
		} catch (DatabaseFailureException e) {
			GraphicHandler.popUpMsg(AlertType.ERROR, e.getMessage());
			closeOffersSection();
		}
		
		FilteredList<OfferBean> filteredList = new FilteredList<>((ObservableList<OfferBean>)list, p -> true);
		table.itemsProperty().set(filteredList);
		
		allRadio.selectedProperty().addListener((observable, oldValue, newValue) -> showAll(filteredList));
		actRadio.selectedProperty().addListener((observable, oldValue, newValue) -> showActive(filteredList));
		expRadio.selectedProperty().addListener((observable, oldValue, newValue) -> showExpired(filteredList));
		
		numCol.setCellFactory(tc -> {
			TableCell<OfferBean, Integer> cell = new TableCell<>();
	        cell.setOnMouseClicked(event -> openOfferDetails(cell.getItem()));     
	        return cell;
    	});
	}
	
    private void openOfferDetails(Integer id) {
		OfferBean bean = new OfferBean();
		try {
			bean = bean.getOffer(id);
		} catch (DatabaseFailureException e) {
			GraphicHandler.popUpMsg(AlertType.ERROR, e.getMessage());
		}
			
		Stage stage = (Stage)offersPane.getScene().getWindow();			
		stage.setScene(GraphicHandler.switchScreen(Scenes.OFFER, new OfferDetailsGraphic(bean)));
	}

	private void showActive(FilteredList<OfferBean> list) {
    	list.setPredicate(data -> {
	      boolean showItem = true;
	      showItem = showItem && (data.getExpiration().isAfter(LocalDate.now()));
	      return showItem;
    	});
    }

    private void showAll(FilteredList<OfferBean> list) {
    	list.setPredicate(data -> true);
    }

    private void showExpired(FilteredList<OfferBean> list) {
    	list.setPredicate(data -> {
  	      boolean showItem = true;
  	      showItem = showItem && (data.getExpiration().isBefore(LocalDate.now()));
  	      return showItem;
      	});
    }
	
	@FXML
    void closeOffersSection() {
		Stage st = (Stage)offersPane.getScene().getWindow();
    	st.close();
    }
}
