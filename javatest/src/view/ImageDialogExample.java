package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ImageDialogExample {
    
    static int score =0;
    private static ArrayList<String> linesWord = new ArrayList<String>();
    private static ArrayList<String> linesImages = new ArrayList<String>();
    
    public ImageDialogExample() throws IOException{
        this.LoadData();
        this.init();
    }
    
    private static void showInformationDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Spelling Quiz", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void showTestDialog(ImageIcon image,String word) {
        JTextField textField = new JTextField();
        Object[] inputComponents = {new JLabel(image), textField};
        int option = JOptionPane.showConfirmDialog(null, inputComponents, "Spelling Quiz", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String userInput = textField.getText().trim().toLowerCase();
            // Kiểm tra tính đúng đắn của từ nhập vào

                if (userInput != null && userInput.equals(word)) {
                    score++;
                    showInformationDialog("Correct!");
                } else {
                    showInformationDialog("Incorrect!");
                }
        }
    }
    
    public void LoadData() throws IOException{
        File file = new File("C:\\Users\\admin\\OneDrive\\Documents\\fileExam.txt");
        try(FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)){
            while(true){
                String line = br.readLine();
                if(line == null || line.isEmpty()) break;
                String[] data = line.split(("[+]"));
                String images = data[0].trim(), word = data[1].trim();
                linesImages.add(images);
                linesWord.add(word);

            }
            br.close();
            fr.close();
        }
    }
    private void init() throws FileNotFoundException, IOException{
        
        Random random = new Random();
        ArrayList<Integer> usedValues = new ArrayList<>(); // tạo array để lưu giá trị đã xuất hiện
        
        for(int i=0;i<5;i++){
            int randomIndex; //tạo biến số để lưu chỉ số ngẫu nhiên
            do{
               randomIndex = random.nextInt(linesWord.size());//gán giá trị cho biến số 
              }while(usedValues.contains(randomIndex));//nếu số đó đã có trong array thì bỏ qua
               usedValues.add(randomIndex);//nếu không thì lưu số đó vào array
               //gán chuỗi kí tự lấy ngẫu nhiên trong file bằng chỉ số randomIndex vào biến
               String random_Line_Word = linesWord.get(randomIndex);
               String random_Line_Image = linesImages.get(randomIndex);
               //tạo biến hình ảnh icon có địa chỉ là random_Line_Image
               ImageIcon icon = new ImageIcon(random_Line_Image);
               //gọi hàm và chạy chương trình
               showTestDialog(icon,random_Line_Word);
        }
        
        showInformationDialog("Your score is " + score + "/" + 5);
        
    }

    public static void main(String[] args) throws IOException {
        
        new ImageDialogExample();
    }
}