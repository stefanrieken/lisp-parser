(define greeting "Hello, ")
(define resolvewho ("me"))

(define hello (who)
	(println (+ greeting who "!"))
)

; ohja comments doen het ook gewoon

(
	(define resolvewho ("who?"))
	(hello resolvewho)
)
(hello resolvewho)