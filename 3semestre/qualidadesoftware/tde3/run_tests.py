import unittest
import sys

loader = unittest.TestLoader()
tests = loader.discover(start_dir='tests')
runner = unittest.TextTestRunner(verbosity=2)
res = runner.run(tests)
sys.exit(0 if res.wasSuccessful() else 1)
