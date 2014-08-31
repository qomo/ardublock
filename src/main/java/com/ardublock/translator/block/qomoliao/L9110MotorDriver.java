package com.ardublock.translator.block.qomoliao;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class L9110MotorDriver extends TranslatorBlock {

	public L9110MotorDriver(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		TranslatorBlock speed_l = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock speed_r = this.getRequiredTranslatorBlockAtSocket(1);

		if((speed_l instanceof NumberBlock)&(speed_r instanceof NumberBlock)){
			int speed_left = Integer.parseInt(speed_l.toCode());
			int speed_right = Integer.parseInt(speed_r.toCode());
			speed_left = speed_left>255?255:(speed_left<-255?-255:speed_left);
			speed_right = speed_right>255?255:(speed_right<-255?-255:speed_right);
			return "__ardublockL9110MotorLeft("+ speed_left +");\n__ardublockL9110MotorRight(" + speed_right + ");\n";
		}else if(speed_l instanceof NumberBlock){
			int speed_left = Integer.parseInt(speed_l.toCode());
			speed_left = speed_left>255?255:(speed_left<-255?-255:speed_left);
			return "__ardublockL9110MotorLeft("+speed_left+");\n__ardublockL9110MotorRight(" + speed_r.toCode() +");\n";
		}else if(speed_r instanceof NumberBlock){
			int speed_right = Integer.parseInt(speed_r.toCode());
			speed_right = speed_right>255?255:(speed_right<-255?-255:speed_right);
			return "__ardublockL9110MotorLeft(" + speed_l.toCode() + ");\n__ardublockL9110MotorRight("+speed_right+");\n";
		}
		else{
			return "__ardublockL9110MotorLeft("+speed_l.toCode()+");\n__ardublockL9110MotorRight("+speed_r.toCode()+");\n";
		}				
	}

}
