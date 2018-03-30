package com.csci6461.team13.simulator;

import com.csci6461.team13.simulator.ui.basic.Program;
import com.csci6461.team13.simulator.util.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhiyuan
 *
 * TestPrograms for test purpose only
 *
 * this file contains two programs that will be used for developing and
 * testing programs, and then you can generate binary format of these programs
 *
 * Note: initial data can only be stored in addresses under 32, any address above
 * can not be directly accessed
 * */
public class TestPrograms {

    // use space to separate numbers
    private static final int PROGRAM_1_SEPARATOR = 32;
    private static final int PROGRAM_1_MAX = 20;

    // sentence separator .
    private static final int PROGRAM_2_SEPARATOR = 46;
    // max 6 sentences
    private static final int PROGRAM_2_MAX = 6;

    // char preset
    private static final int CHAR_0 = 48;
    private static final int CHAR_EQUAL = 61;

    private static Program one = new Program();
    private static Program two = new Program();

    private TestPrograms() {
    }

    static {
        two.setDescription("");
        two.setInitAddrIndex(Const.PROG_INIT_STORAGE_ADDR);
        List<String> init = new ArrayList<>();
        List<String> loop = new ArrayList<>();
        List<String> reader = new ArrayList<>();

        two.putInitData(30, PROGRAM_2_MAX);
        two.putInitData(17, PROGRAM_2_SEPARATOR);
        // storage start
        two.putInitData(26, 500);
        // 10 current char
        // 11 number storage index
        // 12 input count
        // 13 return address for reader
        // 14 storage index

        two.putInstructionList(Const.PROG_INIT_STORAGE_ADDR, init);
        two.putInstructionList(18, loop);
        two.putInstructionList(19, reader);

        // set return address of reader to loop start
        init.add("LDR 0,0,0,18");
        init.add("STR 0,0,0,13");
        // set storage start value to 12
        init.add("LDR 0,0,0,26");
        init.add("STR 0,0,0,14");
        // jump to reader
        init.add("JMA 0,0,1,19");

        // loop
        // load current input number index
        loop.add("LDR 0,0,0,12");
        // check if input number index == Max
        loop.add("SMR 0,0,0,30");
        // not zero, jump to reader
        loop.add("JNE 0,0,1,19");
        // count reached max
        loop.add("LDR 0,0,0,12");
        loop.add("OUT 0,0,1,1");
        loop.add("HLT 0,0,0,0");

        // reader, read a complete sentence
        reader.add("IN 0,0,0,0");
        reader.add("OUT 0,0,0,1");
        // store char to both 10 and storage index
        reader.add("STR 0,0,0,10");
        reader.add("STR 0,0,1,14");
        // increase storage address
        // load storage index
        reader.add("LDR 0,0,0,14");
        // increase index by 1
        reader.add("AIR 0,0,0,1");
        // store new storage index
        reader.add("STR 0,0,0,14");
        // load current char
        reader.add("LDR 0,0,0,10");
        // subtract separator, a constant
        reader.add("SMR 0,0,0,17");
        // if it's not a sentence end
        // to reader start
        reader.add("JNE 0,0,1,19");
        // else
        // increase input count by 1
        // then return to stored address
        // load input count
        reader.add("LDR 0,0,0,12");
        // increase count by 1
        reader.add("AIR 0,0,0,1");
        // store new input count
        reader.add("STR 0,0,0,12");
        // return to stored address
        reader.add("JMA 0,0,1,13");
    }

    static {

        one.setDescription("Read 21 numbers from keyboard, compare the last " +
                "one with previous 20 numbers, print the number closest the " +
                "value of the last number. Input numbers are separated with " +
                "one ' '(space)");

        // max count
        one.putInitData(30, PROGRAM_1_MAX);
        // ' '
        one.putInitData(17, PROGRAM_1_SEPARATOR);
        one.putInitData(7, CHAR_0);
        one.putInitData(8, CHAR_EQUAL);
        // number storage start
        one.putInitData(26, 500);
        // 11 number storage index
        // 12 input count
        // 13 return address for reader
        // 14 a single number
        // 15 current closest
        // 16 difference

        List<String> init = new ArrayList<>();
        List<String> loop = new ArrayList<>();
        List<String> reader = new ArrayList<>();
        List<String> assembler = new ArrayList<>();
        List<String> comparator = new ArrayList<>();
        List<String> replace = new ArrayList<>();
        List<String> printer = new ArrayList<>();

        one.putInstructionList(Const.PROG_INIT_STORAGE_ADDR, init);
        one.putInstructionList(18, loop);
        one.putInstructionList(19, reader);
        one.putInstructionList(27, assembler);
        one.putInstructionList(25, comparator);
        one.putInstructionList(29, replace);
        one.putInstructionList(28, printer);

        // set return address of reader to loop start
        init.add("LDR 0,0,0,18");
        init.add("STR 0,0,0,13");
        // add one more to max
        init.add("LDR 0,0,0,30");
        init.add("AIR 0,0,0,1");
        init.add("STR 0,0,0,30");
        // jump to reader
        init.add("JMA 0,0,1,19");

        // loop
        // load current input number index
        loop.add("LDR 0,0,0,12");
        // check if input number index == Max
        loop.add("SMR 0,0,0,30");
        // not zero, jump to reader
        loop.add("JNE 0,0,1,19");
        // count reached max
        // put last one into 14
        loop.add("LDR 0,0,1,11");
        loop.add("STR 0,0,0,14");
        // decrease number storage index by one
        loop.add("LDR 0,0,0,11");
        loop.add("SIR 0,0,0,1");
        loop.add("STR 0,0,0,11");
        // jump to replace
        loop.add("JMA 0,0,1,29");

        // reader, read a complete number
        reader.add("IN 0,0,0,0");
        reader.add("OUT 0,0,0,1");
        // store char to EA
        reader.add("STR 0,0,0,10");
        // subtract separator, a constant
        reader.add("SMR 0,0,0,17");
        // if it's a valid char
        // to assembler
        reader.add("JNE 0,0,1,27");
        // else
        // increase input count by 1
        // then return to stored address
        // load input count
        reader.add("LDR 0,0,0,12");
        // increase count by 1
        reader.add("AIR 0,0,0,1");
        // store new input count
        reader.add("STR 0,0,0,12");
        // return to stored address
        reader.add("JMA 0,0,1,13");

        // word assembler
        // get input count
        assembler.add("LDR 0,0,0,12");
        // get storage index = input count + start
        assembler.add("AMR 0,0,0,26");
        // store storage index
        assembler.add("STR 0,0,0,11");
        // get word
        assembler.add("LDR 0,0,1,11");
        // assemble, the char to add is stored in 10
        // multiply by 10
        assembler.add("LDA 2,0,0,10");
        // rx = 0, ry = 2
        assembler.add("MLT 0,2,0,10");
        assembler.add("AMR 1,0,0,10");
        // subtract 48
        assembler.add("SMR 1,0,0,7");

        // store the assembled word
        assembler.add("STR 1,0,1,11");
        // back to reader
        assembler.add("JMA 0,0,1,19");

        //comparator
        // load current input number index
        comparator.add("LDR 0,0,0,11");
        // check if number storage index == Max
        comparator.add("SMR 0,0,0,26");
        // if zero, jump to printer
        comparator.add("JZ 0,0,1,28");
        // decrease number storage index by one
        comparator.add("LDR 0,0,0,11");
        comparator.add("SIR 0,0,0,1");
        comparator.add("STR 0,0,0,11");
        // get next number
        comparator.add("LDR 0,0,1,11");
        // get abs val of next
        comparator.add("SMR 0,0,0,14");
        comparator.add("ABS 0,0,0,0");
        // subtract the number to compare with
        comparator.add("SMR 0,0,0,16");
        // if greater or equal, do not replace
        comparator.add("JGE 0,0,1,25");
        // back to comparator start
        comparator.add("JMA 0,0,1,29");

        // replace
        // get next number
        replace.add("LDR 0,0,1,11");
        // store into 15
        replace.add("STR 0,0,0,15");
        // get the difference
        replace.add("SMR 0,0,0,14");
        replace.add("ABS 0,0,0,0");
        // store the difference
        replace.add("STR 0,0,0,16");
        // go to comparator
        replace.add("JMA 0,0,1,25");

        // printer
        printer.add("LDR 0,0,0,8");
        printer.add("OUT 0,0,0,1");
        printer.add("LDR 0,0,0,15");
        // print the final number as integer
        printer.add("OUT 0,0,1,1");
        printer.add("HLT 0,0,0,0");

    }

    public static Program getOne() {
        return one;
    }

    public static Program getTwo() {
        return two;
    }

}