(define greeting "Hello, ")
(define resolvewho ("me"))

(define hello (who)
	(println (+ greeting who "!"))
)

; what does 'who' resolve to in the below code block?

(
	(define resolvewho () ("who?"))
	(hello resolvewho)
)
(hello resolvewho)