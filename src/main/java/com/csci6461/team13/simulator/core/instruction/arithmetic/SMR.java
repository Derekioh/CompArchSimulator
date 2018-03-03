package com.csci6461.team13.simulator.core.instruction.arithmetic;

import com.csci6461.team13.simulator.core.CPU;
import com.csci6461.team13.simulator.core.instruction.ExecutionResult;
import com.csci6461.team13.simulator.core.MCU;
import com.csci6461.team13.simulator.core.Registers;
import com.csci6461.team13.simulator.core.instruction.Instruction;
import com.csci6461.team13.simulator.util.Const;
import com.csci6461.team13.simulator.util.CoreUtil;

public class SMR extends Instruction {
    
    // Subtract memory from register
    @Override
    public ExecutionResult execute(CPU cpu) {
        Registers registers = cpu.getRegisters();
        MCU mcu = cpu.getMcu();
        registers.setMAR(getEffectiveAddress(mcu, registers));

        registers.setMBR(mcu.getFromCache(registers.getMAR()));

        int result = registers.getR(this.getR()) - registers.getMBR();

        //overflow
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
			registers.setCCByBit(Const.ConditionCode.OVERFLOW.getValue(), true);
		} else {
			// if we do not have an overflow, we update the value of register
			registers.setR(this.getR(), result);
		}

        return ExecutionResult.CONTINUE;
    }
}