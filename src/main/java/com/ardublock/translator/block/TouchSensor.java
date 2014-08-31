package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class TouchSensor extends TranslatorBlock
{
	public static final String ARDUBLOCK_TOUCH_SENSOR_DEFINE = "uint8_t readCapacitivePin(int pinToMeasure) {\n"+
		"// Variables used to translate from Arduino to AVR pin naming\n"+
		"volatile uint8_t* port;\n"+
		"volatile uint8_t* ddr;\n"+
		"volatile uint8_t* pin;\n"+
		"// Here we translate the input pin number from\n"+
		"// Arduino pin number to the AVR PORT, PIN, DDR,\n"+
		"// and which bit of those registers we care about.\n"+
		"byte bitmask;\n"+
		"port = portOutputRegister(digitalPinToPort(pinToMeasure));\n"+
		"ddr = portModeRegister(digitalPinToPort(pinToMeasure));\n"+
		"bitmask = digitalPinToBitMask(pinToMeasure);\n"+
		"pin = portInputRegister(digitalPinToPort(pinToMeasure));\n"+
		"// Discharge the pin first by setting it low and output\n"+
		"*port &= ~(bitmask);\n"+
		"*ddr |= bitmask;\n"+
		"delay(1);\n"+
		"// Make the pin an input with the internal pull-up on\n"+
		"*ddr &= ~(bitmask);\n"+
		"*port |= bitmask;\n"+
		"\n"+
		"// Now see how long the pin to get pulled up. This manual unrolling of the loop\n"+
		"// decreases the number of hardware cycles between each read of the pin,\n"+
		"// thus increasing sensitivity.\n"+
		"uint8_t cycles = 17;\n"+
		"if (*pin & bitmask) { cycles = 0;}\n"+
		"else if (*pin & bitmask) { cycles = 1;}\n"+
		"else if (*pin & bitmask) { cycles = 2;}\n"+
		"else if (*pin & bitmask) { cycles = 3;}\n"+
		"else if (*pin & bitmask) { cycles = 4;}\n"+
		"else if (*pin & bitmask) { cycles = 5;}\n"+
		"else if (*pin & bitmask) { cycles = 6;}\n"+
		"else if (*pin & bitmask) { cycles = 7;}\n"+
		"else if (*pin & bitmask) { cycles = 8;}\n"+
		"else if (*pin & bitmask) { cycles = 9;}\n"+
		"else if (*pin & bitmask) { cycles = 10;}\n"+
		"else if (*pin & bitmask) { cycles = 11;}\n"+
		"else if (*pin & bitmask) { cycles = 12;}\n"+
		"else if (*pin & bitmask) { cycles = 13;}\n"+
		"else if (*pin & bitmask) { cycles = 14;}\n"+
		"else if (*pin & bitmask) { cycles = 15;}\n"+
		"else if (*pin & bitmask) { cycles = 16;}\n"+
		"\n"+
		"// Discharge the pin again by setting it low and output\n"+
		"// It's important to leave the pins low if you want to\n"+
		"// be able to touch more than 1 sensor at a time - if\n"+
		"// the sensor is left pulled high, when you touch\n"+
		"// two sensors, your body will transfer the charge between\n"+
		"// sensors.\n"+
		"*port &= ~(bitmask);\n"+
		"*ddr |= bitmask;\n"+
		"\n"+
		"return cycles;\n"+
		"}\n";

	public TouchSensor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addDefinitionCommand(ARDUBLOCK_TOUCH_SENSOR_DEFINE);
		String ret = "readCapacitivePin(";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + ")";
		return codePrefix + ret + codeSuffix;
	}

}
