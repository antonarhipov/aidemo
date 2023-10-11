## IntelliJ AI assistant demo

### Features to demonstrate:

On any element, preferably a class, hit Alt+Enter and select AI actions. For instance, Alt+Enter on TheAlgorithm class, select AI actions:

1. Call **Explain 'TheAlgorithm' class** action, and see the results in the chat. The code implements a variation of a [Convex Hull](https://en.wikipedia.org/wiki/Convex_hull) algorithm that isn't as trivial as any sort algorithm or Fibonacci
2. Try either **renaming** run method of TheAlgorithm class or **extract** a new function from the run method. The AI completion kicks in with a little delay. The AI-suggested names are more descriptive compared to the default suggestion by the IDE which is based on the names and types of the variables and methods
3. **AI actions -> Find problems in selection**.
4. **AI actions -> Suggest refactoring**. See the result and adjust the constraints via chat
5. After refactoring, it's worth documenting the code. **AI actions -> Write documentation**
6. .. or event generate tests: **AI actions -> Start new chat with selection**. This will start a new chat and copy the selected code block. Add any request that you'd like to execute for this code. For instance, "generate unit tests for the following code:". However, it this action is going to be used often, it makes sense to add this as a custom prompt to the user library. In the chat window, on the bottom-left, click the floppy button. A dialog will pop up. Delete the code fragment and replace it with **$SELECTION** variable, give the prompt a name, and save. Now the custom prompt is available via the AI actions quick list and could be reused for any code.

