# Chapter 2

> The means of combination satisfy the closure property, which permits us to easily build up complex designs. Finally, all the tools for abstracting procedures are available to us for abstracting means of combination for painters.

> We have also obtained a glimpse of another crucial idea about languages and program design. This is the approach of _stratified design_, the notion that a complex system should be structured as a sequence of levels that are described using a sequence of languages.

> Each level is constructed by combining parts that are regarded as primitive at that level, and the parts constructed at each level are used as primitives at the next level.

> The language used at each level of a stratified design has **primitives, means of combination, and means of abstraction** appropriate to that level of detail.

__

> Stratified design helps make programs _robust_, that is, it makes it likely that small changes in a specification will require correspondingly small changes in the program.

> In general, each level of a stratified design provides a different vocabulary for expressing the characteristics of the system, and a different kind of ability to change it.

__

> (...) we indulge in a little wishful thinking, as we did in designing the rational-number implementation. If we had a means for representing algebraic expressions, we should be able to tell whether an expression is a sum, a product, a constant, or a variable.

> We should also be able to construct expressions from parts.