Bazel build command:
`bazel build //A2_http:threaded_echo_server  `

Program structure:

![](https://github.ccs.neu.edu/ptmphuong/cs5700_assignment/blob/main/A2_http/program_structure.png)


Notes:
* Connection cannot close in Chrome (I still can't figure out why). But it works properly on Postman and Safari.
* For api/evalexpression stats include both success requests and bad requests on the Status page. That means bad requests are also counted in the last min/hour/24hrs/lifetime count, and listed in the expression list.
