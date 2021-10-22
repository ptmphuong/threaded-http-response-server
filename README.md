<h4> Simple multithreaded server that reads Http requests and responses using only java.io.InputStream, java.io.OutputStream </h3>

<h2> Features </h2>

* `/api/evalexpression`: evaluate arithmetic expressions and return results.
* `/api/gettime`: get the local time on the server
* `/status.html`: return an HTML page with status info of the server: 
  * Number of each API call during the last minute, hour, 24 hours, and lifetime.
  * Most recent 10 expressions clients submitted

<h2> Build </h2>

* Install [Bazel](https://docs.bazel.build/versions/main/install.html)
* Bazel build command: `bazel build threaded_echo_server`
* Binary file is in `bazel-bin` folder.

<h2> Program structure </h2>

![](https://github.com/ptmphuong/threaded-http-response-server/blob/master/doc/program_structure1.png)

Detailed description and requirement: [link](https://github.com/ptmphuong/threaded-http-response-server/blob/master/doc/Programming%20Assignment%202.pdf)
