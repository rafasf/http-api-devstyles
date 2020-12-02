# HTTP API Development Styles

This a playground exercising different workflows to develop and maintain HTTP APIs.

## The Approaches

Code-first or Spec-first. Usually, that is what we find when researching about HTTP API development approaches. The problem of creating, maintaining and evolving HTTP API goes way beyond these two approaches.

So, instead of jumping directly to one of those, we can look at the problem through a few different lenses.

- Lens 1: The provider.
- Lens 2: The consumer.
- Lens 3: The relationship between provider and consumer.
- Lens 4: Quality assurance.

## Take 1

The Product Group has an offering and wants to expose the information to other groups within the organization. Given they are the authority about what a "Product" is, they are in the best position to propose what information to expose.

They have a couple of choices. 
Create a functioning HTTP API and ask for feedback.
Create a specification (à la OpenAPI) and ask for feedback.

With the first option, a team will think about how that API should initially look like and behave. That information will end-up in a card, be prioritized, played and then released. Now we will be able to get some feedback from future consumers, make changes, release, get feedback and repeat.

The lead time between thinking about the API and the first feedback can be considerable. Also, how would the information be described in the card? A text? A few snippets of JSON along with some HTTP codes?

> ### Todo
> Would an OpenAPI spec be more appropriate to describe the desired state?
> If so, why not use that to get the initial feedback?
