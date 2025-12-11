.text
.globl main
main:
addi $t1, $zero, 20
seat $t1
addi $t1, $zero, 0x11
lights $t1
addi $t1, $zero, 65
ac $t1
addi $t1, $zero, 60
setspd $t1
addi $t1, $zero, 5
addi $t2, $zero, 2
mul $t1, $t1, $t2
throttle $t1
addi $t1, $zero, -4
addi $t2, $zero, 2
div $t1, $t1, $t2
throttle $t1
addi $t1, $zero, 10
steer $t1
addi $t1, $zero, -30
steer $t1
ebrake
pophood


