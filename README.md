Solutions List
=============
- I think Imma rely on Radiance for as much GUI stuff as possible
   - See https://github.com/kirill-grouchnikov/radiance

- Data Gathering
   - We'll need a "program manager", a "program", and a "data gatherer"
      - The program manager will hold the program, execute the program, and handling events
      - The program will be created from whatever stuff the user gives us, essentially just something we can execute
         - Depending on how we do this it could literally just be java byte code (in which case we'll need to program our own JVM that will be managed by the "program manager"
      - The "data gatherers" will hold their data, will have methods for accessing this data, will have the ability to READ but **NOT** change the state of a currently executing program, and will be triggered via event
         - By forcing read only we can execute all data gatherers at the same time, massively speeding up program execution
         - Gatherers will have to "register" with the manager, which will trigger them when the requested event is fired
            - For example, a "DuplicateMemoryFinder" (which would extend the "DataGatherer" interface) might register for the "memory change" event, and might store a list of sections of memory that it believes to be duplicated (mby with some context around it so this info can be interpreted?)
               - Maybe it stores it's data as a "ProgramFrame" which contains information about the state of the program at an instance, and some associated data (ie the data we believe to be duplicated?)
                  - This could be extended to other gatherers by changing the assoc data & would allow a single "get" function shared by all gatherers
                  - 
TODO List
==============

- Load in program
    - Via source code
        - Give source code, require they specify a main class (for everything else make this optional)
    - Via port connection?
        - Ie via currently running program
    - Via JAR
    - Via class files
    - Program fragments?
        - I want to take a function (calculate fibbonaci or smthn) and a range of inputs to do all of this information gathering
            -Range over multiple types of data, so 1-5 for numbers, aaaa-ZZZZ for all 4 letter words from all a's to all z's of both cases, over a range of characters (mby mark it with a control character? so #aaaa-ZZZZ All 4 character strings with char values between 'a' and 'Z'?), over different bases, AND give the user the ability to give a program (function / jar / whatever, same as higher layer) that supplies the range of inputs
- Gather a bunch of data
    - Runtime
    - Number and speed of disk accesses
    - Number and life span of threads
        - Sorted by thread type (ie what concrete object each thread is)
        - Sort by time created and died (Like a timeline of threads)
        - Give some sort of collapse function for programs that spin off thousands of threads of the same couple of types
        - And maybe something for programs that do massive weird stuff with hundreds of different types of threads
    - Memory usage
        - Maybe something to show redundent memory use (like the same stuff stored multiple times at the same time)
    - Function speed
        - Average time it takes each function to run
        - Store the inputs and states for extraordinary cases
            - This'll catch functions that run fine on all but one or two cases where they go insanely slow
- Display this data
- It'd be nice if I could use this as part of a build process like a test, so I can say "Fail this test if OSCA says u'll take more than 5 seconds to run"
- Custom software themeing, I want to be able to customize everything to different themes, different header, X button etc, and create my own themes using some sort of file format
- I want to be able to install this, add it to the path variable, register as software etc automatically. One click on an installation wizard then I can run via the windows button.

Nasty Broken Stuff
==================
