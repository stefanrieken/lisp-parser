
(define greeting "Hello, ")
(define who "who")
(define hello (method (who) (println (+ greeting who "!"))))
(hello "world")
(define greeting "Goodbye, cruel ")
(hello "world")
