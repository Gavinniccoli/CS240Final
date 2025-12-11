package mars.mips.instructions.customlangs;
import mars.mips.hardware.*;
import mars.*;
import mars.simulator.Exceptions;
import mars.util.*;
import mars.mips.instructions.*;

public class CarAssembly extends CustomAssembly{

    private static final int CAR_SPEED = 20;
    private static final int CAR_STEER = 21;
    private static final int CAR_LIGHTS = 22;
    private static final int CAR_AC = 23;
    private static final int CAR_SEAT = 24;
    private static final int CAR_STATUS = 25;
    private static final int GAS_LEVEL = 26;

    @Override
    public String getName(){
        return "Car Assembly";
    }

    @Override
    public String getDescription(){
        return "Assembly language to let your computer control a Car NEWNEWNEWNEWEDITION";
    }

    @Override
    protected void populate() {
        //BASIC INSTRUCTIONS
        instructionList.add(
                new BasicInstruction("ADD $t1, $t2, $t2",
                        "ADD : add registers $s + $t storing in $td",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 100000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int rd = operands[0];
                                int rs = operands[1];
                                int rt = operands[2];
                                int res = RegisterFile.getValue(rs)+RegisterFile.getValue(rt);
                                RegisterFile.updateRegister(rd, res);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("sub $t1, $t2, $t3",
                        "SUB : subtract registers $t2 - $t3 storing in $t1",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 100010",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int rd = operands[0];
                                int rs = operands[1];
                                int rt = operands[2];
                                int res = RegisterFile.getValue(rs)-RegisterFile.getValue(rt);
                                RegisterFile.updateRegister(rd, res);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("mul $t1,$t2, $t3",
                        "MUL : multiply registers $t3 * $t2 storing in $t1",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 011000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int rd = operands[0];
                                int rs = operands[1];
                                int rt = operands[2];
                                long res = (long)RegisterFile.getValue(rs)*(long)RegisterFile.getValue(rt);
                                RegisterFile.updateRegister(rd, (int)res);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("addi $t1,$t2,-100",
                        "Addition immediate with overflow : set $t1 to ($t2 plus signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "001000 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = operands[2] << 16 >> 16;
                                int sum = add1 + add2;
                                // overflow on A+B detected when A and B have same sign and A+B has other sign.
                                if ((add1 >= 0 && add2 >= 0 && sum < 0)
                                        || (add1 < 0 && add2 < 0 && sum >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow", Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], sum);
                            }
                        }));
//        instructionList.add(
//                new BasicInstruction("li $t1,-100",
//                        "LI : load imm into register $t1",
//                        BasicInstructionFormat.I_FORMAT,
//                        "001001 00000 fffff iiiiiiiiiiiiiiii",
//                        new SimulationCode() {
//                            public void simulate(ProgramStatement statement) throws ProcessingException {
//                                int[] operands = statement.getOperands();
//                                int rt = operands[0];
//                                int imm = operands[1];
//                                RegisterFile.updateRegister(rt, imm);
//                            }
//                        }));
        instructionList.add(
                new BasicInstruction("move $t1,$t2",
                        "move : move $t1 into $t2",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss 00000 fffff 00000 100001",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int rd = operands[0];
                                int rs = operands[1];
                                int val = RegisterFile.getValue(rs);
                                RegisterFile.updateRegister(rd, val);

                            }
                        }));
        instructionList.add(
                new BasicInstruction("div $t1,$t2, $t3",
                        "DIV : divide registers $t2 / $t3 storing in $t1",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 011010",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int rd = operands[0];
                                int rs = operands[1];
                                int rt = operands[2];
                                int denominator = RegisterFile.getValue(rt);
                                int res;
                                if (denominator==0){
                                    res = 0;
                                }
                                else{
                                    res = (int)RegisterFile.getValue(rs)/denominator;
                                }
                                RegisterFile.updateRegister(rd, res);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("and $t1,$t2, $t3",
                        "AND : $t1 = $t2 & $t3",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 100100",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int rd = operands[0];
                                int rs = operands[1];
                                int rt = operands[2];
                                int res = RegisterFile.getValue(rs) & RegisterFile.getValue(rt);
                                RegisterFile.updateRegister(rd, res);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("or $t1,$t2, $t3",
                        "OR : $t1 = $t2 | $t3",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 100101",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int rd = operands[0];
                                int rs = operands[1];
                                int rt = operands[2];
                                int res = RegisterFile.getValue(rs) | RegisterFile.getValue(rt);
                                RegisterFile.updateRegister(rd, res);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("xor $t1,$t2, $t3",
                        "XOR : $t1 = $t2 ^ $t3",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 100110",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int rd = operands[0];
                                int rs = operands[1];
                                int rt = operands[2];
                                int res = RegisterFile.getValue(rs) ^ RegisterFile.getValue(rt);
                                RegisterFile.updateRegister(rd, res);
                            }
                        }));
        instructionList.add(
                new BasicInstruction("slt $t1,$t2, $t3",
                        "SLT : $t1 = 1 if $t2 < $t3 else 0",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 101010",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int rd = operands[0];
                                int rs = operands[1];
                                int rt = operands[2];
                                int sVAl = RegisterFile.getValue(rs);
                                int tVal = RegisterFile.getValue(rt);
                                int res = (sVAl < tVal)? 1 : 0;
                                RegisterFile.updateRegister(rd, res);
                            }
                        }));
        //CREATIVE INSTRUCTIONS
        instructionList.add(
                new BasicInstruction("throttle $t1",
                        "throttle : change the speed by the value in $t1",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 010000",
                        new SimulationCode() {
                            public void simulate(ProgramStatement statement) throws ProcessingException {
                                int[] operands = statement.getOperands();
                                int rs = operands[0];
                                int delta = RegisterFile.getValue(rs);
                                int speed = RegisterFile.getValue(CAR_SPEED);
                                long newSpeed = (long)speed + (long)delta;
                                if (newSpeed < 0){
                                    newSpeed = 0;
                                }
                                RegisterFile.updateRegister(CAR_SPEED, (int) newSpeed);
                                SystemIO.printString("Your speed is now: "+newSpeed+" \n");
                            }
                        }));
        instructionList.add(
                new BasicInstruction("setspd $t1",
                        "SETSPD : dirrectly set the speed right away to $t1",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 010001",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int rs = operands[0];
                                int v = RegisterFile.getValue(rs);
                                if (v < 0) {
                                    v = 0;
                                }
                                RegisterFile.updateRegister(CAR_SPEED, v);
                                SystemIO.printString("Your speed is: "+v+" \n");
                            }
                        }));
        instructionList.add(
                new BasicInstruction("steer $t1",
                        "STEER : Adjusts the steering angle by ($t1)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 010010",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int rs = operands[0];
                                int delta = RegisterFile.getValue(rs);
                                int angle = RegisterFile.getValue(CAR_STEER);
                                int newAngle = angle + delta;
                                if (newAngle < -90){
                                    newAngle = -90;
                                } else if (newAngle > 90) {
                                    newAngle = 90;
                                }
                                RegisterFile.updateRegister(CAR_STEER, newAngle);
                                SystemIO.printString("You are turned at a: "+newAngle+" degree angle \n");
                            }
                        }));
        instructionList.add(
                new BasicInstruction("setsteer $t1",
                        "SETSTEER : set the steering angle straight to ($t1)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 010011",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int rs = operands[0];
                                int angle = RegisterFile.getValue(rs);
                                RegisterFile.updateRegister(CAR_STEER, angle);
                                SystemIO.printString("You are turned at a: "+angle+" degree angle \n");
                            }
                        }));
        instructionList.add(
                new BasicInstruction("ebrake",
                        "EBRAKE : sets speed to 0 and raises ebrake status flag. ($t1)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 00000 00000 00000 00000 010100",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                RegisterFile.updateRegister(CAR_SPEED, 0);
                                int status = RegisterFile.getValue(CAR_STATUS);
                                status |= 0x1;
                                RegisterFile.updateRegister(CAR_STATUS, status);
                                SystemIO.printString("Your car is now stopped \n");
                            }
                        }));
        instructionList.add(
                new BasicInstruction("lights $t1",
                        "LIGHTS : sets the lights bitmask to ($t1)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 010101",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int rs = operands[0];
                                int mask = RegisterFile.getValue(rs);
                                RegisterFile.updateRegister(CAR_LIGHTS, mask);

                                if (mask == 0x01){
                                    SystemIO.printString("the headlights are now on \n");
                                }if(mask == 0x02 ){
                                    SystemIO.printString("the high beams are now on \n");
                                }if (mask == 0x04) {
                                    SystemIO.printString("the left turn signal is now on \n");
                                }if (mask == 0x08) {
                                    SystemIO.printString("the right turn signal is now on \n");
                                }if (mask == 0x10) {
                                    SystemIO.printString("the hazard lights are now on \n");
                                }if (mask == 0)
                                    SystemIO.printString("the lights are now off \n");
                            }
                        }));
        instructionList.add(
                new BasicInstruction("ac $t1",
                        "AC : Adjusts ac by ($t1)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 010110",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int rs = operands[0];
                                int acVal = RegisterFile.getValue(rs);
                                RegisterFile.updateRegister(CAR_AC, acVal);
                                SystemIO.printString("the car is now: "+acVal+" fahrenheit \n");
                            }
                        }));
        instructionList.add(
                new BasicInstruction("seat $t1",
                        "SEAT : Adjusts the seat position by ($t1)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 010111",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int rs = operands[0];
                                int seatPos = RegisterFile.getValue(rs);
                                RegisterFile.updateRegister(CAR_SEAT, seatPos);
                                SystemIO.printString("the seat is now set at: "+seatPos +" \n");
                            }
                        }));
        instructionList.add(
                new BasicInstruction("pophood",
                        "POPHOOD : pops the hood",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 00000 00000 00000 00000 011000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int status = RegisterFile.getValue(CAR_STATUS);
                                status |= 0x2;
                                RegisterFile.updateRegister(CAR_STATUS, status);
                                SystemIO.printString("the hood is now popped \n");
                            }
                        }));
        instructionList.add(
                new BasicInstruction("fillgas $t1",
                        "FILLGAS : fills the gas by ($t1)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 011001",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int rs = operands[0];
                                int amt = RegisterFile.getValue(rs);
                                int curr = RegisterFile.getValue(GAS_LEVEL);
                                long newLevel = (long)amt + (long)curr;
                                if (newLevel < 0){
                                    newLevel = 0;
                                } else if (newLevel > 100) {
                                    newLevel = 100;
                                }
                                RegisterFile.updateRegister(GAS_LEVEL,(int)newLevel);
                                SystemIO.printString("the gas is now: "+newLevel +"% full \n");
                            }
                        }));
    }
}