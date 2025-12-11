.text
.globl main
main:
addi $t1, $zero, 75
fillgas $t1
addi $t1, $zero, 30
setspd, $t1
addi $t1, $zero, 0x01
lights $t1
addi $t1, $zero, 0x02
lights $t1
addi $t1, $zero, 0x10
lights $t1
addi $t1, $zero, 20
steer, $t1
addi $t1, $zero, 7
throttle $t1
ebrake
addi $t1, $zero, 0
lights $t1
