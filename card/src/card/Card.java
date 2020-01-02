package card;

import java.io.Serializable;



public class Card implements Comparable<Card>,Serializable{
    private int value;  //花色值+牌面值
    private String colorName;//花色：方块 梅花 红桃 黑心
    private int colorNumber;//花色值：1方块 2梅花 3红桃 4黑心
    private String card;//牌
    private int number;//牌面值：A取14


    public Card(String colorName,int colorNumber,String card,int number,int value){
        this.colorName=colorName;
        this.number=number;
        this.card=card;
        this.colorNumber=colorNumber; 
        this.value=value;
    }

    public int getColorNumber() {
		return colorNumber;
	}

	public void setColorNumber(int colorNumber) {
		this.colorNumber = colorNumber;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getColorName() {
    	
    	
    	return colorName;
    } 
    public String getCard() {
        return card;
    }
    public int getNumber() {
        return number;
    }


    public int compareTo(Card o) {
        // TODO Auto-generated method stub
        return o.value-this.value;
    }


}
