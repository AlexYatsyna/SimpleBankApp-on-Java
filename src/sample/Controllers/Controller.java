package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.sql.SQLException;
import sample.db.DatabaseHandler;
import sample.models.clients.*;
import  sample.models.credits.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Controller {

    Client client;
    boolean isLoan=false;
    boolean isInstallmentPlan = false;
    boolean isLeasing = false;
    boolean is6M=false;
    boolean is1Y=false;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group auntificationgroup;

    @FXML
    private Button signin;

    @FXML
    private Button signup;

    @FXML
    private Group creditsgroup;

    @FXML
    private RadioButton rad_btn_1;

    @FXML
    private ToggleGroup credit;

    @FXML
    private RadioButton rad_btn_2;

    @FXML
    private RadioButton rad_btn_3;

    @FXML
    private Group sumgroup;

    @FXML
    private Slider slidersum;

    @FXML
    private Label textsum;

    @FXML
    private Group dategroup;

    @FXML
    private Button takeCredit;

    @FXML
    private RadioButton rad_btn_4;

    @FXML
    private ToggleGroup returnDate1;

    @FXML
    private RadioButton rad_btn_5;

    @FXML
    private Group signupgroup;

    @FXML
    private TextField CFN;

    @FXML
    private TextField CLN;

    @FXML
    private TextField CA;

    @FXML
    private TextField CP;

    @FXML
    private TextField CPAS;

    @FXML
    private TextField CPFN;

    @FXML
    private TextField CPLN;

    @FXML
    private TextField CPA;

    @FXML
    private TextField CPP;

    @FXML
    private Group signingroup;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private Button continueSign;

    @FXML
    private Button exit;

    @FXML
    void initialize() throws SQLException {
        signin.setOnAction(actionEvent -> {
            auntificationgroup.setVisible(false);
            signingroup.setVisible(true);
            continueSign.setVisible(true);
            exit.setVisible(true);
        });

        continueSign.setOnAction(actionEvent -> {
            if(signingroup.isVisible())
            {
            String phoneNumber = login.getText().trim();
            String pass = password.getText().trim();
            if (phoneNumber.length() > 0 && pass.length() >= 1)
                loginClient(phoneNumber, pass);
            }
            else
                if(signupgroup.isVisible())
                {
                    signUpClient();
                }
        });

        signup.setOnAction(actionEvent -> {
            auntificationgroup.setVisible(false);
            signupgroup.setVisible(true);
            continueSign.setVisible(true);
            exit.setVisible(true);
        });

        exit.setOnAction(actionEvent -> {
            setOffAll();
            auntificationgroup.setVisible(true);
        });

        slidersum.setOnDragDetected(actionEvent ->
        {
            textsum.setText(Integer.toString((int)slidersum.getValue()));
        });

        takeCredit.setOnAction(actionEvent -> {
            regCredit();
        });

        rad_btn_1.setOnAction(actionEvent -> {
            isLoan=true;
            isInstallmentPlan=false;
            isLeasing=false;
        });

        rad_btn_2.setOnAction(actionEvent -> {
            isLoan=false;
            isInstallmentPlan=false;
            isLeasing=true;
        });

        rad_btn_3.setOnAction(actionEvent -> {
            isLoan=false;
            isInstallmentPlan=true;
            isLeasing=false;
        });

        rad_btn_4.setOnAction(actionEvent -> {
            is6M = true;
            is1Y=false;
        });

        rad_btn_5.setOnAction(actionEvent -> {
            is1Y=true;
            is6M=false;
        });



    }

    private void loginClient(String phoneNumber, String pass) {
        DatabaseHandler db = new DatabaseHandler();
        client =db.getClient(phoneNumber,pass);
        if (client == null) {
            showAlertWithoutHeaderText("We have no such client!");
            login.clear();
            password.clear();
            signingroup.setVisible(false);
            continueSign.setVisible(false);
            auntificationgroup.setVisible(true);

        }
        else {
            login.clear();
            password.clear();
            signingroup.setVisible(false);
            continueSign.setVisible(false);
            creditsgroup.setVisible(true);
            sumgroup.setVisible(true);
            dategroup.setVisible(true);
            exit.setVisible(true);
        }
    }

    private  void signUpClient()
    {
        String cfn = CFN.getText().trim();
        String cln = CLN.getText().trim();
        String ca = CA.getText().trim();
        String cp = CP.getText().trim();
        String cpas = CPAS.getText().trim();
        String cpfn = CPFN.getText().trim();
        String cpln = CPLN.getText().trim();
        String cpa = CPA.getText().trim();
        String cpp = CPP.getText().trim();
        if(cfn.equals("") || cln.equals("") || ca.equals("") || cp.equals("") ||
                cpas.equals("") || cpfn.equals("") || cpln.equals("") || cpa.equals("") || cpp.equals("")){
            showAlertWithoutHeaderText("Invalid data!");
            return;
        }
        if((!cp.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$") || (!cpp.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$"))))
        {
         continueSign.setDisable(false);
         showAlertWithoutHeaderText("invalid phone number");
         return;
        }
        else{
            DatabaseHandler db = new DatabaseHandler();
            Person contactPerson = db.getPersonById(Integer.parseInt(db.getContactPersonIdByPhone(cpp)));
            if(contactPerson == null){
                contactPerson = new Person(cpfn,cpln,cpa,cpp);
            }
            client = new Client(cfn,cln,ca,cp,contactPerson,cpas);
            Client temp = db.SearchClient(client.getNumberPhone());
            if(temp == null) {
                db.signUpClient(client);
                CFN.clear();
                CLN.clear();
                CA.clear();
                CP.clear();
                CPAS.clear();
                CPFN.clear();
                CPLN.clear();
                CPA.clear();
                CPP.clear();
                signupgroup.setVisible(false);
                continueSign.setVisible(false);
                creditsgroup.setVisible(true);
                sumgroup.setVisible(true);
                dategroup.setVisible(true);
                exit.setVisible(true);

            }
            else {
                showAlertWithoutHeaderText("This client exists");
                CFN.clear();
                CLN.clear();
                CA.clear();
                CP.clear();
                CPAS.clear();
                CPFN.clear();
                CPLN.clear();
                CPA.clear();
                CPP.clear();
                signupgroup.setVisible(false);
                continueSign.setVisible(false);
                auntificationgroup.setVisible(true);
                client = null;
            }
        }

    }

    private void showAlertWithoutHeaderText(String error) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert!");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    private  void regCredit()
    {
        if(isLoan || isLeasing || isInstallmentPlan)
        {
            if(is1Y || is6M)
            {
                DatabaseHandler db = new DatabaseHandler();
                if(isLoan){
                    Loan loan = new Loan((int)slidersum.getValue(), is6M ? LocalDate.of(LocalDate.now().getYear(),
                            LocalDate.now().getMonthValue() +6,LocalDate.now().getDayOfMonth()) :LocalDate.of(LocalDate.now().getYear()+1,
                            LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth()));
                    db.registerLoan(loan,client);
                    setOffRadBtn();
                    return;
                }
                if(isLeasing)
                {
                    Leasing leasing = new Leasing((int)slidersum.getValue(), is6M ? LocalDate.of(LocalDate.now().getYear(),
                            LocalDate.now().getMonthValue() +6,LocalDate.now().getDayOfMonth()) :LocalDate.of(LocalDate.now().getYear()+1,
                            LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth()),LeasingThings.BUILDING);
                    db.registerLeasing(leasing,client);
                    setOffRadBtn();
                    return;
                }
                if(isInstallmentPlan)
                {
                    InstallmentPlan installmentPlan = new InstallmentPlan((int)slidersum.getValue(), is6M ? LocalDate.of(LocalDate.now().getYear(),
                            LocalDate.now().getMonthValue() +6,LocalDate.now().getDayOfMonth()) :LocalDate.of(LocalDate.now().getYear()+1,
                            LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth()));
                    db.registerInstallmentPlan(installmentPlan,client);
                    setOffRadBtn();
                    return;
                }
            }
            else{
                showAlertWithoutHeaderText("Return date not selected!");
            }
        }
        else{
            showAlertWithoutHeaderText("Credit type not selected!");
        }
    }

    private  void  setOffRadBtn(){
        rad_btn_1.setSelected(false);
        rad_btn_2.setSelected(false);
        rad_btn_3.setSelected(false);
        rad_btn_4.setSelected(false);
        rad_btn_5.setSelected(false);
    }

    private  void setOffAll(){
        setOffRadBtn();
        slidersum.setValue(slidersum.getMin());
        textsum.setText(Integer.toString((int)slidersum.getMin()));
        creditsgroup.setVisible(false);
        sumgroup.setVisible(false);
        dategroup.setVisible(false);
        exit.setVisible(false);
        continueSign.setVisible(false);
        signupgroup.setVisible(false);
        signingroup.setVisible(false);
        CFN.clear();
        CLN.clear();
        CA.clear();
        CP.clear();
        CPAS.clear();
        CPFN.clear();
        CPLN.clear();
        CPA.clear();
        CPP.clear();
        login.clear();
        password.clear();
        client = null;
        isLoan=false;
        isInstallmentPlan = false;
        isLeasing = false;
        is6M=false;
        is1Y=false;

    }
}
