// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

(LISTEN)

	@pixel
	M = -1				// pixel = -1
	
	@KBD
	D = M				// D = keyInput()
	@SHOWPIXEL
	D;JNE				// If keyInput() != 0 goto SHOWPIXEL
	
	@pixel
	M = 0				// else pixel = 0
	
	(SHOWPIXEL)
	
	@i
	M = 0				// i = 0
	@KBD
	D = A
	@endScreen		
	M = D - 1			// endScreen = 24575
	@SCREEN
	D = A
	@endScreen
	M = M - D 			
	M = M + 1			// endScreen 24575 - 163844
	@SCREEN
	D = A				// D = SCREEN
	@rowSegment 
	M = D				// rowSegment = SCREEN
	
	(DRAWPIXEL)
		
		@i
		D = M			// D = i
		@endScreen
		D = D - M		// D = i - endScreen
		@LISTEN
		D;JEQ			// If (i - endScreen) = 0 goto LISTEN
		
		@pixel
		D = M			// D = pixel
		@rowSegment
		A = M
		M = D			// rowSegment[i] = pixel
		
		@rowSegment
		M = M + 1		// rowSegment += 1
		@i
		M = M + 1		// i++
		
		@DRAWPIXEL
		0;JMP			// Goto DRAWPIXEL
		
		
@LISTEN
	0;JMP				// Infinite loop