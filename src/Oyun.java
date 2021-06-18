
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates{
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}


public class Oyun extends JPanel implements KeyListener,ActionListener{
    Timer timer = new Timer(5, this);
    private int gecenSure = 0;
    private int harcananMermi = 0;
    private BufferedImage image;
    private ArrayList<Ates> atesler = new ArrayList<Ates>();
    private int atesdirY = 1;
    private int topX= 0;
    private int topdirX=2;
    private int uzayGemisiX= 0;
    private int dirUzayX = 20;
    private int vurus= 0;
    
    public boolean kontrolEt(){
        
        for(Ates ates: atesler){
            if(new Rectangle(ates.getX(),ates.getY(),1,20).intersects(new Rectangle(topX,0,20,20))){
                return true;
            }
        }
        return false;
    }
    
    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("spcship.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setBackground(Color.BLACK);
        timer.start();
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs); //To change body of generated methods, choose Tools | Templates.
        gecenSure += 5;
        grphcs.setColor(Color.LIGHT_GRAY);
        grphcs.fillOval(topX, 0, 20, 20);
        grphcs.drawImage(image, uzayGemisiX, 490,image.getWidth()/10,image.getHeight()/10,this);
        for(Ates ates:atesler){
            if(ates.getY()<0){
            atesler.remove(ates);
            }
        }
        grphcs.setColor(Color.GREEN);
        for(Ates ates:atesler){
            grphcs.fillRect(ates.getX(), ates.getY(), 10, 20);
        }
        
        
    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
        
    }
    
    
    
    @Override
    public void keyTyped(KeyEvent ke) {
       repaint();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int c = ke.getKeyCode();
       if(c == KeyEvent.VK_LEFT){
           if(uzayGemisiX <0){
               uzayGemisiX=0;
           }
           else{
               uzayGemisiX -= dirUzayX;
           }
       }
       else if(c == KeyEvent.VK_RIGHT){
           if(uzayGemisiX >= 750){
               uzayGemisiX=750;
           }
           else{
               uzayGemisiX += dirUzayX;
           }
       }
       else if(c == KeyEvent.VK_SPACE){
           
           atesler.add(new Ates(uzayGemisiX+22,470));
           harcananMermi++;
           
           
       }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
       repaint();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        
        for(Ates ates: atesler){
            ates.setY(ates.getY()-atesdirY);
        }
        topX += topdirX;
        if(topX>=750){
            topdirX = -topdirX;
        }
        if (topX<=0) {
            topdirX = -topdirX;
        }
        if(kontrolEt()==true){
            vurus+=1;
        }
        if(gecenSure==60000){
            
            timer.stop();
            String message = "Oyun bitti!\n"
                    +"Vurulan yıldız sayısı: "+vurus+"\n"+
                    "Harcanan Ateş: "+harcananMermi;
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
            
        }
        
        repaint();
    }
    
}
