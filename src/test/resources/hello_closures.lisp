(define greeting "Hello, ")
(define resolvewho (method () ("me")))

(define hello (method (whoclosure)
	(println (+ greeting (whoclosure) "!")))
)

; what does 'who' resolve to in the below code block?

(
	(define resolvewho (method () ("who?")))
	(hello resolvewho)
)
(hello (method() (resolvewho)))
