import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class Vocabulary {
    // tạo arraylist để lưu thứ tự dữ liệu
    private final ArrayList<String> DataImages = new ArrayList<>(); 
    private final ArrayList<String> DataAudio = new ArrayList<>(); 
    private Clip clip;
    private int index = 0;
    private  JPanel Panel = new JPanel();
    private  JLabel Label = new JLabel();
    private JButton nextButton = new JButton("Next");
    private JButton backButton = new JButton("Back");
    JFrame frame = new JFrame();
    
   public Vocabulary() throws IOException{
        this.init();
       frame.setSize(705,805);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocationRelativeTo(null);
       frame.setTitle("Vocabulary");
       this.LoadData();
       this.setDataToLabel(0);
   }

   // tạo hàm xử lí âm thanh
public void setFileAudio(String soundFileName){			
    try{
        File file = new File(soundFileName);
        AudioInputStream sound = AudioSystem.getAudioInputStream(file);	
        clip = AudioSystem.getClip();
        clip.open(sound);
    }
    catch(IOException | LineUnavailableException | UnsupportedAudioFileException e){}
}
		
public void play(){			
    clip.setFramePosition(0);
    clip.start();   
}

public void setFileImages(String imageFileName){
    File file = new File(imageFileName);
try {
    BufferedImage image = ImageIO.read(file);
    //JLabel imageLabel = new JLabel(new ImageIcon(image));
        ImageIcon icon = new ImageIcon(image);
        Label.setIcon(icon);
        Label.setPreferredSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
} catch (IOException e) {
}
}

public void LoadData() throws IOException{
    File file = new File("C:\\Users\\admin\\OneDrive\\Documents\\flieImage.txt");
    try(FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)){
        while(true){
            String line = br.readLine();
            if(line == null || line.isEmpty()) break;
            String[] data = line.split(("[+]"));
            String images = data[0].trim(), audio = data[1].trim();
            DataImages.add(images);
            DataAudio.add(audio);
            
        }
        br.close();
        fr.close();
    }
}

public void setDataToLabel(int index){
    
    if (index == 0) {
                backButton.setVisible(false);
            }
    
    String pathAudio = DataAudio.get(index);
    String pathImage = DataImages.get(index);
    
    this.setFileAudio(pathAudio);
    this.setFileImages(pathImage); 
}
   public void init(){
             
       JPanel vocabLabel = new JPanel();
       vocabLabel.add(Label);
       Panel.add(Label);
       frame.add(Panel);
       
        nextButton.addActionListener((ActionEvent e) -> {
            index++;
            if (index == DataAudio.size()-1) {
                nextButton.setVisible(false);
            }
            backButton.setVisible(true);
            this.setDataToLabel(index);
        });

        backButton.addActionListener((ActionEvent e) -> {
            index--;
            if (index == 0) {
                backButton.setVisible(false);
            }
            nextButton.setVisible(true);
            this.setDataToLabel(index);
        });
        
        JButton audioButton = new JButton("Play Audio"); 
        audioButton.addActionListener((ActionEvent e) -> {
            this.play();
        });
        // Tạo JPanel cho button 
        JPanel Button = new JPanel();
        Button.setLayout(new FlowLayout());
        Button.add(backButton);
        Button.add(nextButton);
        Button.add(audioButton);

        // Thêm JButton vào JFrame
        frame.add(Button, BorderLayout.SOUTH);
        // Hiển thị JFrame
       frame.pack();
       frame.setVisible(true);
        
   }
   
    public static void main(String[] args) throws IOException {
        new Vocabulary();
    }
}