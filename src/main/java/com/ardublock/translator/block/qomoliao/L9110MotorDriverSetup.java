package com.ardublock.translator.block.qomoliao;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class L9110MotorDriverSetup extends TranslatorBlock {

	public static final String ARDUBLOCK_L9110_MOTOR_LEFT_DEFINE = 
	"\nvoid __ardublockL9110MotorLeft(int speed)\n" +
	"{\n" +
	"speed = speed>255?255:(speed<-255?-255:speed);\n" +
	"if(speed<0){\n" +
		"digitalWrite(L9110_LEFT_DIR_PIN1, LOW);\n" +
		"analogWrite(L9110_LEFT_DIR_PIN2, -speed);\n" +
	"}\n" +
	"else{\n" +
	"	analogWrite(L9110_LEFT_DIR_PIN1, speed);\n" +
	"	digitalWrite(L9110_LEFT_DIR_PIN2, LOW);\n" +
	"}\n" +
	"}\n" ;

	public static final String ARDUBLOCK_L9110_MOTOR_RIGHT_DEFINE = 
	"\nvoid __ardublockL9110MotorRight(int speed)\n" +
	"{\n" +
	"speed = speed>255?255:(speed<-255?-255:speed);\n" +
	"if(speed<0){\n" +
		"digitalWrite(L9110_RIGHT_DIR_PIN1, LOW);\n" +
		"analogWrite(L9110_RIGHT_DIR_PIN2, -speed);\n" +
	"}\n" +
	"else{\n" +
	"	analogWrite(L9110_RIGHT_DIR_PIN1, speed);\n" +
	"	digitalWrite(L9110_RIGHT_DIR_PIN2, LOW);\n" +
	"}\n" +
	"}\n" ;

	public L9110MotorDriverSetup(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		TranslatorBlock pin_dir_l1 = this.getTranslatorBlockAtSocket(0);
		TranslatorBlock pin_dir_l2 = this.getTranslatorBlockAtSocket(1);
		TranslatorBlock pin_dir_r1 = this.getTranslatorBlockAtSocket(2);
		TranslatorBlock pin_dir_r2 = this.getTranslatorBlockAtSocket(3);

		translator.addDefinitionCommand("#define L9110_LEFT_DIR_PIN1 " + pin_dir_l1.toCode());
		translator.addDefinitionCommand("#define L9110_LEFT_DIR_PIN2 " + pin_dir_l2.toCode());
		translator.addDefinitionCommand("#define L9110_RIGHT_DIR_PIN1 " + pin_dir_r1.toCode());
		translator.addDefinitionCommand("#define L9110_RIGHT_DIR_PIN2 " + pin_dir_r2.toCode());
		
		translator.addDefinitionCommand(ARDUBLOCK_L9110_MOTOR_LEFT_DEFINE);
		translator.addDefinitionCommand(ARDUBLOCK_L9110_MOTOR_RIGHT_DEFINE);

		translator.addSetupCommand("pinMode(L9110_LEFT_DIR_PIN1, OUTPUT);");
		translator.addSetupCommand("pinMode(L9110_LEFT_DIR_PIN2, OUTPUT);");
		translator.addSetupCommand("pinMode(L9110_RIGHT_DIR_PIN1, OUTPUT);");
		translator.addSetupCommand("pinMode(L9110_RIGHT_DIR_PIN2, OUTPUT);");

		return "\n";
		
	}

}
