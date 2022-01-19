# TopologicalSort
I've been asked to implement a [topological sort](https://en.wikipedia.org/wiki/Topological_sorting) in several interviews. Since it was fairly common (at least in my experience), I figured I would work through a decent solution. 

The questions asked don't ever mention the topological sort by name, of course. They're usually worded in a way to obfuscate what kind of problem it is. Usually it's posed as some sort of dependency problem (task scheduler, etc). A hallmark of this type of question is that it involves a directed, acyclic graph (again, not something that's usually said outright in the problem statement).

As far as an effective interview question goes, I feel like this isn't a particularly great one. If you've come across the topological sort and understand it well, you should be able to apply it to whatever wording this question morphs into. However, at least for me, the solution isn't something that can be intuited within an hour if you're not already familiar with this context.

# Algorithm

The algorithm I've coded here to produce the topological sort is Kahn's algorithm as described in the linked Wikipedia page above.