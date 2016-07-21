(define greeting "Hello, ")
	(define resolvewho ("who?"))
(define hello (who) (print greeting who "!"))
; asdf
(
	(define resolvewho ("me"))
	(hello resolvewho)
)
