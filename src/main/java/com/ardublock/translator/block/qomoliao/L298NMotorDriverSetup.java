package com.ardublock.translator.block.qomoliao;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class L298NMotorDriverSetup extends TranslatorBlock {

	public static final String ARDUBLOCK_L298N_MOTOR_LEFT_DEFINE = 
	"\nvoid __ardublockL298NMotorLeft(int speed)\n" +
	"{\n" +
	"speed = speed>255?255:(speed<-255?-255:speed);\n" +
	"if(speed<0){\n" +
		"digitalWrite(L298N_LEFT_DIR_PIN1, LOW);\n" +
		"digitalWrite(L298N_LEFT_DIR_PIN2, HIGH);\n" +
		"analogWrite(L298N_LEFT_PWM_PIN, -speed);\n" +
	"}\n" +
	"else{\n" +
	"	digitalWrite(L298N_LEFT_DIR_PIN1, HIGH);\n" +
	"	digitalWrite(L298N_LEFT_DIR_PIN2, LOW);\n" +
	"	analogWrite(L298N_LEFT_PWM_PIN, speed);\n" +
	"}\n" +
	"}\n" ;

	public static final String ARDUBLOCK_L298N_MOTOR_RIGHT_DEFINE = 
	"\nvoid __ardublockL298NMotorRight(int speed)\n" +
	"{\n" +
	"speed = speed>255?255:(speed<-255?-255:speed);\n" +
	"if(speed<0){\n" +
		"digitalWrite(L298N_RIGHT_DIR_PIN1, LOW);\n" +
		"digitalWrite(L298N_RIGHT_DIR_PIN2, HIGH);\n" +
		"analogWrite(L298N_RIGHT_PWM_PIN, -speed);\n" +
	"}\n" +
	"else{\n" +
	"	digitalWrite(L298N_RIGHT_DIR_PIN1, HIGH);\n" +
	"	digitalWrite(L298N_RIGHT_DIR_PIN2, LOW);\n" +
	"	analogWrite(L298N_RIGHT_PWM_PIN, speed);\n" +
	"}\n" +
	"}\n" ;

	public L298NMotorDriverSetup(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		TranslatorBlock pin_pwm_l = this.getTranslatorBlockAtSocket(0);
		TranslatorBlock pin_dir_l1 = this.getTranslatorBlockAtSocket(1);
		TranslatorBlock pin_dir_l2 = this.getTranslatorBlockAtSocket(2);
		TranslatorBlock pin_pwm_r = this.getTranslatorBlockAtSocket(3);
		TranslatorBlock pin_dir_r1 = this.getTranslatorBlockAtSocket(4);
		TranslatorBlock pin_dir_r2 = this.getTranslatorBlockAtSocket(5);
		String definepin = "#define L298N_LEFT_PWM_PIN " + pin_pwm_l.toCode();
		translator.addDefinitionCommand(definepin);
		translator.addSetupCommand("pinMode(L298N_LEFT_DIR_PIN1, OUTPUT);");

		definepin = "#define L298N_LEFT_DIR_PIN1 " + pin_dir_l1.toCode();
		translator.addDefinitionCommand(definepin);
		translator.addSetupCommand("pinMode(L298N_LEFT_DIR_PIN2, OUTPUT);");

		definepin = "#define L298N_LEFT_DIR_PIN2 " + pin_dir_l2.toCode();
		translator.addDefinitionCommand(definepin);
		translator.addSetupCommand("pinMode(L298N_LEFT_PWM_PIN, OUTPUT);");
		
		definepin = "#define L298N_RIGHT_PWM_PIN " + pin_pwm_r.toCode();
		translator.addDefinitionCommand(definepin);
		translator.addSetupCommand("pinMode(L298N_RIGHT_DIR_PIN1, OUTPUT);");
		
		definepin = "#define L298N_RIGHT_DIR_PIN1 " + pin_dir_r1.toCode();
		translator.addDefinitionCommand(definepin);
		translator.addSetupCommand("pinMode(L298N_RIGHT_DIR_PIN2, OUTPUT);");
		
		definepin = "#define L298N_RIGHT_DIR_PIN2 " + pin_dir_r2.toCode();
		translator.addDefinitionCommand(definepin);
		translator.addSetupCommand("pinMode(L298N_RIGHT_PWM_PIN, OUTPUT);");

		translator.addDefinitionCommand(ARDUBLOCK_L298N_MOTOR_LEFT_DEFINE);
		translator.addDefinitionCommand(ARDUBLOCK_L298N_MOTOR_RIGHT_DEFINE);

		return "\n";
		
	}

}
