package usp.wirelezzgame.test;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import usp.wirelezzgame.client.ClientMessageDecoder;
import usp.wirelezzgame.client.ClientMessageEncoder;
import usp.wirelezzgame.core.Jogador;
import usp.wirelezzgame.core.Partida;
import usp.wirelezzgame.core.Time;
import usp.wirelezzgame.core.acao.AcaoAtacarArea;
import usp.wirelezzgame.core.area.Area;
import usp.wirelezzgame.core.area.AreaConquista;
import usp.wirelezzgame.core.captcha.Captcha;
import usp.wirelezzgame.server.ServerMessageEncoder;

public class MainTest {
	
	public static void main(String[] args) {
		server();
		
	}

	public static void client(){

		System.out.println("---dadosJogador--");
		System.out.println(ClientMessageEncoder.dadosJogador("Bruno","Bruno Orlandi","brorlandi"));
		System.out.println("---timeJogador--");
		System.out.println(ClientMessageEncoder.timeJogador(0));
		System.out.println("---interagirArea--");
		System.out.println(ClientMessageEncoder.interagirArea(new AreaConquista(1.0,1.0,1.0,10),1.0,1.0,ClientMessageEncoder.ATACAR_AREA));
		System.out.println("---responderCaptcha--");
		System.out.println(ClientMessageEncoder.responderCaptcha(0, "bla bla"));
		System.out.println("---responderCaptcha--");
		System.out.println(ClientMessageEncoder.mensagemChatTodos("Ze chat"));
		System.out.println("---responderCaptcha--");
		System.out.println(ClientMessageEncoder.mensagemChatTime("Ze chat"));
		
	}
	public static void server(){
		String s = ServerMessageEncoder.nomeServer("The Wirelezz Game - USP Server");
		System.out.println(s);
		
		ClientCallback cc = new ClientCallback();
		ClientMessageDecoder cmd = new ClientMessageDecoder(cc);
		cmd.parse(s);
		
		
		Partida p = new Partida();

		Time t = new Time("Alpha", Time.Cor.VERMELHO);		
		int tid = p.addNewTime(t);

		Jogador j = new Jogador("Bruno", "Bruno Orlandi", "brorlandi");
		p.addNewJogador(j,tid);
		j = new Jogador("Gustavo", "Gustavo Blanco", "gu_blanco");
		p.addNewJogador(j,tid);

		t = new Time("Bravo", Time.Cor.AZUL);		
		tid = p.addNewTime(t);

		j = new Jogador("Marcus", "Marcus da Silva", "mogsilva");
		p.addNewJogador(j,tid);
		j = new Jogador("Nihey", "Nihey Takizawa", "nihey");
		p.addNewJogador(j,tid);
		
		s = ServerMessageEncoder.timesData(p.getTimes());
		System.out.println("--timesData---");
		System.out.println(s);
		cmd.parse(s);
		
		AreaConquista a = new AreaConquista(1.0, 1.0, 1.0, 5);
		p.addNewArea(a);
		a.alterarNivelDefesa(15);
		a.alterarNivelDefesa(-10);
		a.setTimeID(t.getID());
		
		a = new AreaConquista(2.0, 2.0, 2.0, 5);
		p.addNewArea(a);
		a = new AreaConquista(3.0, 3.0, 3.0, 5);
		p.addNewArea(a);
		
		s = ServerMessageEncoder.areasData(p.getAreas());		
		System.out.println("---areasData--");
		System.out.println(s);		

		/*
		s = ServerMessageEncoder.jogadorIdTime(j);		
		System.out.println("---jogadorIdTime--");
		System.out.println(s);
		
		s = ServerMessageEncoder.novoJogador(j);
		System.out.println("--novoJogador--");
		System.out.println(s);

		
		/*
		EventQueue.invokeLater(new Runnable()
        {
            public void run(){
                ImageFrame frame = new ImageFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                Object result = JOptionPane.showInputDialog(frame, "Digite os caracteres da Imagem:");
                if(result != null){
                	boolean re = false;
                try {
					re = frame.component.captcha.respostaCaptcha((String)result);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
                if(re){
                	System.out.println("Caracteres Corretos!");
                }else{
                	System.out.println("Caracteres INCORRETOS!");
                }
                }
                System.exit(0);
            }
        }
        );
        
        */
		
/*
		System.out.println("---mensagemResultadoCaptcha--");
		System.out.println(ServerMessageEncoder.mensagemResultadoCaptcha(true));
		System.out.println("---mensagemPontosRecurso--");
		j.setPontosRecurso(8);
		System.out.println(ServerMessageEncoder.mensagemPontosRecurso(j,ServerMessageEncoder.GASTOU_PONTOS));
		System.out.println("--mensagemAlteraDefesaArea---");
		System.out.println(ServerMessageEncoder.mensagemAlteraDefesaArea(a, j, ServerMessageEncoder.AREA_ATACADA));
		System.out.println("--mensagemAreaConquistada---");
		System.out.println(ServerMessageEncoder.mensagemAreaConquistada(a,j));
		System.out.println("--mensagemVitoriaTime---");
		System.out.println(ServerMessageEncoder.mensagemVitoriaTime(t));
		System.out.println("--mensagemJogadorDesconectou---");
		System.out.println(ServerMessageEncoder.mensagemJogadorDesconectou(j));
		System.out.println("--mensagemChat---");
		System.out.println(ServerMessageEncoder.mensagemChat(j,ServerMessageEncoder.CHAT_TODOS,"Ze Chat"));
		*/
	}
}
@SuppressWarnings("serial")
class ImageFrame extends JFrame{

	public ImageComponent component;
    public ImageFrame(){
        setTitle("ImageTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        component = new ImageComponent();
        add(component);
        
        //setSize(component.getWidth(), component.getHeight());

    }

    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 200;
}


class ImageComponent extends JComponent{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Image image;
    public Captcha captcha;
    public ImageComponent(){
    	/*
        try{
            File image2 = new File("img.jpg");
            BufferedImage img = ImageIO.read(image2);
            String imgstr;
            imgstr = encodeToString(img, "png");
            //System.out.println(imgstr);
             */
        	
    		captcha = new Captcha(new AcaoAtacarArea(new Jogador("Bruno", "TeStE", "aaa"),new AreaConquista(1.0, 1.0, 5.0, 10)));
    		System.out.println("Img: "+captcha.getImage());
    		System.out.println("Viewstate: "+captcha.getViewstate());
            image = decodeToImage(captcha.getImage());
            
            //image = ImageIO.read(image2);

	/*
        }
        catch (IOException e){
            e.printStackTrace();
        }
     */
    }

    public int getWidth(){
    	return image.getWidth(null);
    }
    public int getHeight(){
    	return image.getHeight(null);
    }
    
    /**
     * Decode string to image
     * @param imageString The string to decode
     * @return decoded image
     */
    public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Encode image to string
     * @param image The image to encode
     * @param type jpeg, bmp, ...
     * @return encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
    
    public void paintComponent (Graphics g){
        if(image == null) return;
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);

        g.drawImage(image, 0, 0, this);

        for (int i = 0; i*imageWidth <= getWidth(); i++)
            for(int j = 0; j*imageHeight <= getHeight();j++)
                if(i+j>0) g.copyArea(0, 0, imageWidth, imageHeight, i*imageWidth, j*imageHeight);
    }

}
