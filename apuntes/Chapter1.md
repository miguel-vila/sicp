## Chapter 1

>A powerful programming language is more than just a means for instructing a computer to perform tasks. The language also serves as a framework within which we organize our ideas about processes. (...) Every powerful language has three mechanisms for accomplishing this:

>* **primitive expressions**, which represent the simplest entities the language is concerned with,
>* **means of combination**, by which compound elements are built from simpler ones, and
>* **means of abstraction**, by which compound elements can be named
and manipulated as units.

___
>To evaluate a combination, do the following:

>1. Evaluate the subexpressions of the combination.
>2. Apply the procedure that is the value of the letmost subexpression (the operator) to the arguments that are the values of the other subexpressions (the operands).

___
>Notice that the evaluation rule given above does not handle definitions. For instance, evaluating `(define x 3)` does not apply define to two arguments, one of which is the value of the symbol `x` and the other of which is `3`, since the purpose of the define is precisely to associate `x` with a value. (That is, `(define x 3)` is not a _combination_.)

>Such exceptions to the general evaluation rule are called _special forms_.

___
>(The _substitution model_) can be taken as a model that determines the “meaning” of procedure application. 

>Thee purpose of the substitution is to help us think about procedure application, not to provide a description of how the interpreter really works. (...) In practice, the “substitution” is accomplished by using a local environment for the formal parameters.

>The substitution model is only the first of these models—a way to get started thinking formally about the evaluation process. **In general, when modeling phenomena in science and engineering, we begin with simplified, incomplete models.** (..) when we address in Chapter 3 the use of procedures with “mutable data,” we will see that the substitution model breaks down and must be replaced by a more complicated model of procedure application.

___
>This alternative “fully expand and then reduce” evaluation method is known as _normal-order evaluation_, in contrast to the “evaluate the arguments and then apply” method that the interpreter actually uses, which is called _applicative-order evaluation_.

>(...) normal-order and applicative-order evaluation produce the same value.

___
>The importance of this decomposition strategy is not simply that one is dividing the program into parts. After all, we could take any large program and divide it into parts—the first ten lines, the next ten lines, the next ten lines, and so on. **Rather, it is crucial that each procedure accomplishes an identifiable task that can be used as a module in defining other procedures.**

___
>A formal parameter of a procedure has a very special role in the procedure definition, in that it doesn’t matter what name the formal parameter has. Such a name is called a **bound variable**, and we say that the procedure definition binds its formal parameters. The meaning of a procedure definition is unchanged if a bound variable is consistently renamed throughout the definition. If a variable is not bound, we say that it is **free**. The set of expressions for which a binding defines a name is called the **scope** of that name. In a procedure definition, the bound variables declared as the formal parameters of the procedure have the body of the procedure as their scope.

___
```scheme
(define (sqrt x)
	(define (good-enough? guess x)
		(< (abs (- (square guess) x)) 0.001))
	(define (improve guess x) (average guess (/ x guess)))
	(define (sqrt-iter guess x)
		(if (good-enough? guess x)
			guess
			(sqrt-iter (improve guess x) x)))
	(sqrt-iter 1.0 x))
```

>(...) it is not necessary to pass `x` explicitly to each of these procedures. Instead, we allow `x` to be a free variable in the internal definitions, as shown below. Then `x` gets its value from the argument with which the enclosing procedure `sqrt` is called. This discipline is called **lexical scoping**.

```scheme
(define (sqrt x)
	(define (good-enough? guess)
		(< (abs (- (square guess) x)) 0.001))
	(define (improve guess)
		(average guess (/ x guess)))
	(define (sqrt-iter guess)
		(if (good-enough? guess)
			guess
			(sqrt-iter (improve guess))))
	(sqrt-iter 1.0))
```

En la primera versión el argumento `x` es pasado explícitamente, en cambio en la segunda es usado a partir del _scope_, dando lugar a una definición mas límpia y simple.

**Lexical scoping:** cuando _variables libres_ en la definición de un _procedimiento_ son definidas como las variables que se encuentran en el _entorno_ en el que se definió ese _procedimiento_.

En JavaScript, con variables mutables, una clausura puede ser definida con respecto a estado mutable (y causar un poco de confusión si no se tiene cuidado). En un lenguaje con variables inmutables no hay que preocuparse por eso.

___
>**Lexical scoping** dictates that free variables in a procedure are taken to refer to bindings made by enclosing procedure definitions; that is, they are looked up in the environment in which the procedure was defined.

___
>Like the novice chess player, we don’t yet know the common paerns of usage in the domain. We lack the knowledge of which moves are worth making (which procedures are worth defining). We lack the experience to predict the consequences of making a move (executing a procedure).

___
>In contrasting iteration and recursion, we must be careful not to confuse the notion of a recursive _process_ with the notion of a recursive _procedure_. When we describe a procedure as recursive, we are referring to the syntactic fact that the procedure definition refers (either directly or indirectly) to the procedure itself. But when we describe a process as following a pattern that is, say, linearly recursive, we are speaking about how the process evolves, not about the syntax of how a procedure is written.
