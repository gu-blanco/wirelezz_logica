package usp.wirelezzgame.core.captcha;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import usp.wirelezzgame.core.acao.AcaoAbstract;

public class Captcha {
	
	private int mID;
	private String mViewstate;
	private String mImage;
	private AcaoAbstract mAcao;
	
	public Captcha(AcaoAbstract acao) {
		mAcao = acao;
		try {
			String json = UrlRequest.getContents("http://wirelezzcaptcha.herokuapp.com/get_captcha.php");
			JSONParser parser=new JSONParser();
			Object obj=parser.parse(json);
			JSONObject request=(JSONObject)obj;
			long status = (long) request.get("status");
			//System.out.println(request.get("message"));
			if(status == 0) //sem erros
			{
				JSONObject data=(JSONObject)request.get("obj");
				mImage = (String) data.get("img");
				mViewstate = (String) data.get("viewstate");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean respostaCaptcha(String resposta) throws Exception{

			
			String json;
			try {
				json = UrlRequest.getContents(
"http://wirelezzcaptcha.herokuapp.com/answer_captcha.php?viewstate="+ URLEncoder.encode(mViewstate, "UTF-8") + 
"&captcha=" + URLEncoder.encode(resposta, "UTF-8")+
"&jogador=" + URLEncoder.encode(mAcao.getJogador().getNomeCompleto(), "UTF-8") );
			
			JSONParser parser=new JSONParser();
			Object obj=parser.parse(json);
			JSONObject request=(JSONObject)obj;
			long status = (long) request.get("status");
			//System.out.println(request.get("message"));
			if(status == 0) //acertou
			{
				return true;
			}			
			else if(status == 1) //errou
			{
				return false;
			}	
			else if(status == 2) // erro no servidor
			{
				throw new Exception((String) request.get("message"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public int getID() {
		return mID;
	}
	public void setID(int mID) {
		this.mID = mID;
	}
	public String getImage() {
		return mImage;
	}
	public String getViewstate() {
		return mViewstate;
	}
	public AcaoAbstract getAcao() {
		return mAcao;
	}
	public void setAcao(AcaoAbstract mAcao) {
		this.mAcao = mAcao;
	}	

}
