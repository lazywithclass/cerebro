var assert = require('assert')
var check = require('../lib/source')

describe('simple project', function() {

  it('returns true', function() {
    assert.equal(check(10, 20), true)
  })

})
