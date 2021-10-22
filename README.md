Bazel build command:
`bazel build //A2_http:threaded_echo_server  `

Homework requirements: [link](https://github.ccs.neu.edu/ptmphuong/cs5700_assignment/blob/main/A2_http/doc/Programming%20Assignment%202.pdf)

Program structure:

![](https://github.com/ptmphuong/threaded-http-response-server/blob/master/doc/program_structure1.png)


Notes:
* Connection cannot close in Chrome (I still can't figure out why). But it works properly on Postman and Safari.
* For api/evalexpression stats include both success requests and bad requests on the Status page. That means bad requests are also counted in the last min/hour/24hrs/lifetime count, and listed in the expression list.
