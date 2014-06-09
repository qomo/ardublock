package com.ardublock.translator.block.qomoliao;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class IRread extends TranslatorBlock {

	public IRread(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("IRremote.h");

		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);

		String number = translatorBlock.toCode();
		String defpin = "int RECV_PIN" + number + " = " + number + ";";
		translator.addDefinitionCommand(defpin);
		// translator.addDefinitionCommand("ISDC"+number+";");
		translator.addDefinitionCommand("IRrecv irrecv" + number + "(RECV_PIN" + number + ");");
		translator.addDefinitionCommand("decode_results " + "ir_results" + number +";");
		translator.addSetupCommand("irrecv"+number+".enableIRIn();");
		String ARDUBLOCK_IR_READ_DEFINE = 
		"\nunsigned long __ardublockIRread"+number+"()\n" + 
		"{	int isdecode = irrecv" + number + ".decode(&ir_results" + number + ");\n" +
		"	unsigned long irdata = ir_results" + number + ".value;\n" +
		"	if(isdecode){\n" +
		"		irrecv" + number + ".resume();\n" +
		"		return irdata;\n}\n" +
		"	else\n" +
		"		return 0;\n}";
		translator.addDefinitionCommand(ARDUBLOCK_IR_READ_DEFINE);

		String ret = "__ardublockIRread"+number+"()";
		return ret;
	}

}
