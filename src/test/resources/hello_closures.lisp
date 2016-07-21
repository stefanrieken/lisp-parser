(define greeting "Hello, ")
	(define resolvewho ("who?"))
(define hello (who) (println (+ greeting who "!")))
; asdf
(
	(define resolvewho ("me"))
	(hello resolvewho)
)
