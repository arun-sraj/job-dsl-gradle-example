listView('test-view1') {
    jobs {
        name('test1')
    }
   columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}
