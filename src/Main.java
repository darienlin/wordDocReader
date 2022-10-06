import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            //declares main file used to compared to and all variables related
            File mainFile = new File("D:\\MBZ10.04\\022822 contiuned sell\\091622_Jackpot Wireless eBay_CONTINUE SELL.docx");
            FileInputStream mainFis = new FileInputStream(mainFile);
            XWPFDocument mainDoc = new XWPFDocument(mainFis);
            List<XWPFParagraph> mainParagraphList = mainDoc.getParagraphs();
            //XWPFParagraph mainParagraph = mainParagraphList.get(1);
            ArrayList<String> mainStrParagraphList = new ArrayList<String>();

            //declares file that is used to be compared and all variables related
            File compFile = new File("D:\\MBZ10.04\\022822 contiuned sell\\ContinueSelling_03012022\\Ebay_ThousandGear_03012022.docx");
            FileInputStream compFis = new FileInputStream(compFile);
            XWPFDocument compDoc = new XWPFDocument(compFis);
            List<XWPFParagraph> compParagraphList = compDoc.getParagraphs();
            ArrayList<String> compStrParagraphList = new ArrayList<String>();

            ArrayList<String> finalAsinList = new ArrayList<String>();
            boolean diff = false;
            boolean equAsin = false;


            //mainStrParagraphList.add(mainParagraph.getText());
            //System.out.println(mainStrParagraphList.get(0));
            //System.out.println(compAsin.substring(8, 18).equals(mainAsin.substring(8, 18)));
            //iterates and prints through all the text in the document
            for (XWPFParagraph compParagraph : compParagraphList) {
                String compAsin = compParagraph.getText();
                if(compAsin.indexOf("https://") != -1)
                    continue;

                if (compAsin.length() > 4) {
                    if (compAsin.substring(0, 4).equals("ASIN")) // && mainAsin.substring(0, 4).equals("ASIN")
                        compAsin = compAsin.substring(8, 18);
                    else if (compAsin.length() == 10 || compAsin.length() == 11)
                        compAsin = compAsin.substring(0, 10);
                    else if(compAsin.length() == 12)
                        compAsin = compAsin.substring(0, 12);
                    else continue;
                }
                for (XWPFParagraph mainParagraph : mainParagraphList) {
                    String mainAsin = mainParagraph.getText();
                    if(mainAsin.indexOf("https://") != -1)
                        continue;
                    //System.out.println(compAsin + " " + mainAsin);
                    if (mainAsin.length() > 4) {
                        if (mainAsin.substring(0, 4).equals("ASIN")) // && mainAsin.substring(0, 4).equals("ASIN")
                            mainAsin = mainAsin.substring(8, 18);
                        else if (mainAsin.length() == 10 || mainAsin.length() == 11)
                            mainAsin = mainAsin.substring(0, 10);
                        else if(mainAsin.length() == 12)
                            mainAsin = mainAsin.substring(0, 12);
                        else continue;
                    }

                    if (compAsin.equals(mainAsin)) { //need to work on this
                        diff = false;
                        break;
                    }

                    else {
                        diff = true;
                    }
                }

               if(diff)
                   compStrParagraphList.add(compAsin);

            }

            for(int i = 0; i < compStrParagraphList.size(); i++)
                System.out.println(compStrParagraphList.get(i));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}