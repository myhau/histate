# histate
State history (intellij plugin)



Planned features:

1. Java:
- instrumentation using java agent (+ probably bytebuddy)
- show history of method's arguments and method's return values (from past executions on test env / unit tests)
- show history of exceptions that a particular line thrown in the past
- show history of object variables state 
- show history of static variables state
- each "state" change should have context associated with it (package, thread, which line of code changed it, time, stacktrace)
- debugger-like view for a variable (persisted history of state of each object)


Ideas:
- detect or mark by hand pure functions so that you can quickly check how it will work if some inputs are modified
https://people.eecs.berkeley.edu/~daw/papers/pure-ccs08.pdf
https://people.csail.mit.edu/rinard/paper/vmcai05.purity.ps
