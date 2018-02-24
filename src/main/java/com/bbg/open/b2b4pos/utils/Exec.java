package com.bbg.open.b2b4pos.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***
 * 首先给扑克牌中每张牌设定一个编号，下面算法实现的编号规则如下：  
　　u　　　 红桃按照从小到大依次为：1-13  
　　u　　　 方块按照从小到大依次为：14-26  
　　u　　　 黑桃按照从小到大依次为：27-39  
　　u　　　 梅花按照从小到大依次为：40-52  
　　u　　　 小王为53，大王为54  
　　算法实现如下：  
　　u　　　 首先按照以上编号规则初始化一个包含N个数字的数组(N代表牌的张数，可以是54,48)  
　　u　　　 每次随机从该数组中抽取一个数字，分配给保存玩家数据的数组  
　　实现该功能的代码如下所示：  
 * @author bbg
 *
 */

public class Exec {
	//存牌数组
	int[] pokers = null;
	
	//底牌张数，默认留3张底牌
	int underNum = 3;
	
	//游戏人数，默认三个人
	int playNum = 3;
	//每个玩家牌的张数
	int pokersNum = 0;
	//洗好之后玩家的牌
	int[][] playPokers = null;
	
	//随机数组
	Random random = new Random();

	//剩下的牌的数量
	int lastLength = 0;
	
	public Exec(){
		pokers = new int[54];//不传参默认一副
		lastLength = 54;
		
		//每个玩家牌的张数=(所有牌的张数-底牌张数)/玩牌人数
		pokersNum = (pokers.length - underNum)/playNum;
		playPokers = new int[playNum][pokersNum];
	}
	
	/***
	 * 
	 * @param totalPokers
	 * 需要创建的牌的总数量一副54张
	 * @param underNum
	 * 需要预留的底牌数量
	 * @param playNum
	 * 玩家数量
	 */
	public Exec(int totalPokers, int underNum, int playNum){
		if(totalPokers != 0){
			pokers = new int[totalPokers];
			lastLength = totalPokers;
		}else{
			pokers = new int[54];//传0默认一副
			lastLength = 54;
		}
		this.underNum = underNum;
		this.playNum = playNum;
		
		//每个玩家牌的张数=(所有牌的张数-底牌张数)/玩牌人数
		pokersNum = (pokers.length - underNum)/playNum;
		playPokers = new int[playNum][pokersNum];
	}
	
	
	/**
	 * 获取随机发给玩家的牌（注意，初始化有底牌的需要调用获取底牌方法得到底牌）
	 * @return
	 */
	public int[][] getPlayPokers(){
		//牌的数组赋值
		for (int i = 0; i < pokers.length; i++) {
			pokers[i] = ( i + 1 ) % 54;
			if(pokers[i] == 0){//54除以54取余得到的值是0，在此判断，重新标记为54
				pokers[i] = 54;
			}
		}
		
		int randomInt = 0;
		//根据玩家牌的张数确定发多少圈牌
		for(int i = 0; i < pokersNum; i++){
			//根据玩家数量确定每次发几张
			for(int j = 0; j < playNum; j++){
				//利用类似冒泡的方法发牌
				
				//随机取一张牌发掉
				randomInt = random.nextInt(lastLength);
				playPokers[j][i] = pokers[randomInt];
				
				//把剩余的最后面的一张牌放到已经发掉的牌的位置
				pokers[randomInt] = pokers[lastLength-1];
				//然后剩余牌的数量-1
				lastLength--;
				
			}
		}
		return playPokers;	
	}
	
	public int[] getUnder(){
		int[] under = new int[underNum];//存放底牌的数组
		for(int i = 0; i < underNum; i++){
			under[i] = pokers[i];
		}
		
		return under;
	}
	
	
	/***
	 * 
	 * @param totalPokers
	 * 需要创建的牌的总数量一副54张
	 * @param underNum
	 * 需要预留的底牌数量
	 * @param playNum
	 * 玩家数量
	 */
	public List<String> getList(){
		List<String> list = new ArrayList<String>();
		
		//需要先洗牌发牌
		getPlayPokers();
		
		// 循环输出玩家手中的牌
		for (int i = 0; i < playPokers.length; i++) {
			StringBuffer tempS = new StringBuffer(); 
			for (int j = 0; j < playPokers[i].length; j++) {
				tempS.append(playPokers[i][j]+",");
			}
			list.add(tempS.substring(0, tempS.length()-1));
		}
		
		StringBuffer tempS = new StringBuffer(); 
		// 底牌
		for (int i = 0; i < underNum; i++) {
			tempS.append(pokers[i]+",");
		}
		list.add(tempS.substring(0, tempS.length()-1));
		
		return list;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Exec exec = new Exec(108, 8, 4);
		Exec exec = new Exec();
		List<String> list = exec.getList();
		for (String string : list) {
			System.out.println(string);
		}
	}

}
