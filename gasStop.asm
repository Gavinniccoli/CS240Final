.text
.globl main
main:
addi $t1, $zero, 33
fillgas $t1
addi $t1, $zero, 15
seat $t1
addi $t1, $zero, 1
lights $t1
addi $t1, $zero, 70
ac $t1
addi $t1, $zero, 60
setspd $t1
addi $t1, $zero, 10
throttle $t1
addi $t1, $zero, -20
throttle $t1
addi $t1, $zero, -20
throttle $t1
ebrake
addi $t1, $zero, 50
fillgas $t1
addi $t1, $zero, 5
fillgas $t1
addi $t1, $zero, 0
lights $t1
