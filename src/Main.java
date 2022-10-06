import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner kb = new Scanner(System.in);
            String mainPathName;
            String compPathName = "ploop";


            System.out.print("Enter the main file (bigger file, ex: \"D:\\MBZ10.04\\022822 contiuned sell\\091622_Jackpot Wireless eBay_CONTINUE SELL.docx\"): ");
            mainPathName = kb.nextLine();
            mainPathName = mainPathName.substring(1, mainPathName.length() - 1);

            System.out.println("");

            System.out.print("Enter the comparing file (smaller file, ex: \"D:\\MBZ10.04\\022822 contiuned sell\\Ebay_Jackpot Wireless_03012022.docx\"): ");
            compPathName = kb.nextLine();
            compPathName = compPathName.substring(1, compPathName.length() - 1);

            //runs until user types stop
            while (!compPathName.equals("stop")) {
                //declares main file used to compared to and all variables related
                File mainFile = new File(mainPathName);
                FileInputStream mainFis = new FileInputStream(mainFile);
                XWPFDocument mainDoc = new XWPFDocument(mainFis);
                List<XWPFParagraph> mainParagraphList = mainDoc.getParagraphs();
                //XWPFParagraph mainParagraph = mainParagraphList.get(1);
                ArrayList<String> mainStrParagraphList = new ArrayList<String>();


                //declares file that is used to be compared and all variables related
                File compFile = new File(compPathName);
                FileInputStream compFis = new FileInputStream(compFile);
                XWPFDocument compDoc = new XWPFDocument(compFis);
                List<XWPFParagraph> compParagraphList = compDoc.getParagraphs();
                ArrayList<String> compStrParagraphList = new ArrayList<String>();


                //final ASIN list that contains asin/ebay ID that is present in comp but not in main
                ArrayList<String> finalAsinList = new ArrayList<String>();
                boolean diff = false;
                boolean equAsin = false;


               //loops through comp file
                for (XWPFParagraph compParagraph : compParagraphList) {
                    String compAsin = compParagraph.getText();
                    //if doesnt have id or asin reads html
                    if (compAsin.indexOf("https://") >= 0){
                        String []compAsinHttpParts = (compAsin.split("/"));
                        compAsin = compAsinHttpParts[4].substring(0,12);
                        //System.out.println("compAsin " + compAsin);
                    }

                    //finds asin if cant find then reads for ebay or asin without "ASIN: "
                    if (compAsin.length() > 4) {
                        if (compAsin.substring(0, 4).equals("ASIN"))
                            compAsin = compAsin.substring(8, 18);
                        else if (compAsin.length() == 10 || compAsin.length() == 11)
                            compAsin = compAsin.substring(0, 10);
                        else if (compAsin.length() == 12)
                            compAsin = compAsin.substring(0, 12);
                        else continue;
                    }
                    //loops through main to compare
                    for (XWPFParagraph mainParagraph : mainParagraphList) {
                        String mainAsin = mainParagraph.getText();
                        //same as above
                        if (mainAsin.indexOf("https://") >= 0){
                            String []mainAsinHttpParts = (mainAsin.split("/"));
                            mainAsin = mainAsinHttpParts[4].substring(0,12);
                        }
                        //same as above
                        if (mainAsin.length() > 4) {
                            if (mainAsin.substring(0, 4).equals("ASIN"))
                                mainAsin = mainAsin.substring(8, 18);
                            else if (mainAsin.length() == 10 || mainAsin.length() == 11)
                                mainAsin = mainAsin.substring(0, 10);
                            else if (mainAsin.length() == 12)
                                mainAsin = mainAsin.substring(0, 12);
                            else continue;
                        }

                        //if comp is main then breaks loop but if it isn't then is added
                        if (compAsin.equals(mainAsin)) {
                            diff = false;
                            break;
                        } else {
                            diff = true;
                        }
                    }

                    //adds to finalList
                    if (diff)
                        compStrParagraphList.add(compAsin);

                }

                System.out.println("\n");

                if(compStrParagraphList.size() == 0)
                    System.out.println("Everything in comparing file is in main file");

                else {
                    System.out.println("ASIN or ebay ID that is in the comparing file but not in the main file: ");
                    for (int i = 0; i < compStrParagraphList.size(); i++)
                        System.out.println(compStrParagraphList.get(i));
                }

                System.out.println("\n");

                System.out.print("Enter the comparing file (smaller file, ex: \"D:\\MBZ10.04\\022822 contiuned sell\\Ebay_Jackpot Wireless_03012022.docx\") or \"stop\" to stop run: ");
                compPathName = kb.nextLine();
                if(compPathName.equals("stop"))
                    break;
                compPathName = compPathName.substring(1, compPathName.length() - 1);
            }

            } catch(FileNotFoundException e){
                throw new RuntimeException(e);
            } catch(IOException e){
                throw new RuntimeException(e);
            }
    }
}